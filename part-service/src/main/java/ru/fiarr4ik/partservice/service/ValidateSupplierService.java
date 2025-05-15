package ru.fiarr4ik.partservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.fiarr4ik.partservice.dto.SupplierDto;
import ru.fiarr4ik.partservice.exception.SupplierNotFoundException;

@Service
public class ValidateSupplierService {

    private final RestTemplate restTemplate;

    @Autowired
    public ValidateSupplierService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void validateSupplierExists(Long supplierId) {
        String supplierServiceUrl = "http://localhost:8081/api/suppliers/{supplierId}";

        try {
            restTemplate.getForEntity(supplierServiceUrl, Void.class, supplierId);
        } catch (HttpClientErrorException.NotFound e) {
            throw new SupplierNotFoundException(supplierId);
        } catch (RestClientException e) {
            throw new RuntimeException("Ошибка при проверке поставщика", e);
        }
    }

    public SupplierDto getSupplierById(Long supplierId) {
        ResponseEntity<SupplierDto> response = restTemplate.getForEntity(
                "http://localhost:8081/api/suppliers/" + supplierId, SupplierDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Supplier not found");
        }

        return response.getBody();
    }
}


