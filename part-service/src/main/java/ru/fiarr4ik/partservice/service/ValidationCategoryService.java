package ru.fiarr4ik.partservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.fiarr4ik.partservice.dto.CategoryDto;
import ru.fiarr4ik.partservice.exception.CategoryNotFoundException;

@Service
public class ValidationCategoryService {

    private final RestTemplate restTemplate;

    @Autowired
    public ValidationCategoryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void validateCategoryExists(Long categoryId) {
        String supplierServiceUrl = "http://localhost:8082/api/categories/{categoryId}";

        try {
            restTemplate.getForEntity(supplierServiceUrl, Void.class, categoryId);
        } catch (HttpClientErrorException.NotFound e) {
            throw new CategoryNotFoundException(categoryId);
        } catch (RestClientException e) {
            throw new RuntimeException("Ошибка при проверке категории", e);
        }
    }

    public CategoryDto getCategoryById(Long categoryId) {
        ResponseEntity<CategoryDto> response = restTemplate.getForEntity(
                "http://localhost:8082/api/categories/" + categoryId, CategoryDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Category not found");
        }

        return response.getBody();
    }
}

