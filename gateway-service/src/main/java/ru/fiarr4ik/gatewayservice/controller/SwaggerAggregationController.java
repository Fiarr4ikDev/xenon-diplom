package ru.fiarr4ik.gatewayservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;

import java.util.function.Function;

@RestController
@RequestMapping("/swagger")
public class SwaggerAggregationController {

    private final WebClient webClient;

    private final List<String> serviceNames = List.of(
            "category-service",
            "supplier-service",
            "part-service",
            "inventory-service",
            "gateway-service"
    );

    public SwaggerAggregationController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> aggregateSwagger() {
        return Flux.fromIterable(serviceNames)
                .flatMap(service -> fetchSwaggerDocs(service)
                        .map(doc -> Map.entry(service, doc)))
                .collectMap(Map.Entry::getKey, Map.Entry::getValue)
                .map(this::mergeSwaggerDocs);
    }

    private Mono<Map<String, Object>> fetchSwaggerDocs(String serviceName) {
        String url = "http://" + serviceName + "/v3/api-docs";
        return webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    System.err.println("Ошибка при получении swagger для сервиса: " + serviceName + ", " + e.getMessage());
                    return Mono.just(Collections.emptyMap());
                });
    }

    @SuppressWarnings("checkstyle:RegexpSingleline")
    private Map<String, Object> mergeSwaggerDocs(Map<String, Map<String, Object>> docs) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("openapi", "3.0.1");
        result.put("info", Map.of(
                "title", "Агрегированный API-шлюз",
                "version", "1.0.0",
                "description", """
        Данный API-шлюз обеспечивает интеграцию и агрегацию Swagger-документации всех микросервисов,
        реализующих функциональность по разработке, администрированию и защите базы данных
        для учета запасных частей и комплектующих на примере ООО «Ксенон +».

        Цель проекта — создание единой точке доступа к API различных сервисов, что упрощает взаимодействие
        и повышает эффективность администрирования данных, обеспечивая надёжность и безопасность работы с информацией.
        
        В рамках дипломной работы были реализованы механизмы интеграции, агрегации и унификации
        описания REST API, а также реализованы меры по защите и контролю доступа.
        """,
                "contact", Map.of(
                        "name", "Дементьев Денис Витальевич",
                        "email", "fiarr4ikdev@gmail.com",
                        "url", "https://fiarr4ikdev.github.io/"
                ),
                "license", Map.of(
                        "name", "Apache 2.0",
                        "url", "http://springdoc.org"
                )
        ));

        result.put("paths", new LinkedHashMap<>());
        result.put("components", Map.of("schemas", new LinkedHashMap<>()));

        Map<String, Object> resultPaths = (Map<String, Object>) result.get("paths");
        Map<String, Object> resultSchemas = (Map<String, Object>) ((Map<String, Object>) result.get("components")).get("schemas");

        for (Map.Entry<String, Map<String, Object>> entry : docs.entrySet()) {
            String service = entry.getKey();
            Map<String, Object> doc = entry.getValue();
            if (doc == null || doc.isEmpty()) {
                continue;
            }

            rewriteRefs(doc, name -> service + "_" + name);

            Map<String, Object> paths = (Map<String, Object>) doc.get("paths");
            if (paths != null) {
                for (Map.Entry<String, Object> pathEntry : paths.entrySet()) {
                    resultPaths.put("/" + service + pathEntry.getKey(), pathEntry.getValue());
                }
            }

            Map<String, Object> components = (Map<String, Object>) doc.get("components");
            if (components != null) {
                Map<String, Object> schemas = (Map<String, Object>) components.get("schemas");
                if (schemas != null) {
                    for (Map.Entry<String, Object> schemaEntry : schemas.entrySet()) {
                        resultSchemas.put(service + "_" + schemaEntry.getKey(), schemaEntry.getValue());
                    }
                }
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private void rewriteRefs(Object obj, Function<String, String> prefixer) {
        if (obj instanceof Map<?, ?> mapObj) {
            Map<String, Object> map = (Map<String, Object>) mapObj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if ("$ref".equals(key) && value instanceof String ref && ref.startsWith("#/components/schemas/")) {
                    String original = ref.substring("#/components/schemas/".length());
                    map.put("$ref", "#/components/schemas/" + prefixer.apply(original));
                } else {
                    rewriteRefs(value, prefixer);
                }
            }
        } else if (obj instanceof List<?> list) {
            for (Object item : list) {
                rewriteRefs(item, prefixer);
            }
        }
    }

}
