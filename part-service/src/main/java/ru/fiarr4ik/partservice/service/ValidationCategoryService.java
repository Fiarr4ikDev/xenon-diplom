package ru.fiarr4ik.partservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.fiarr4ik.partservice.exception.CategoryNotFoundException;
import ru.fiarr4ik.partservice.exception.SupplierNotFoundException;

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
}

