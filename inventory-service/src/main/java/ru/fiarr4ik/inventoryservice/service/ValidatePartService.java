package ru.fiarr4ik.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.fiarr4ik.inventoryservice.dto.PartResponseDto;
import ru.fiarr4ik.inventoryservice.exception.PartNotFoundException;

@Service
public class ValidatePartService {

    private final RestTemplate restTemplate;

    @Autowired
    public ValidatePartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validatePartExist(Long partId) {
        String supplierServiceUrl = "http://part-service/api/parts/{partId}";

        try {
            restTemplate.getForEntity(supplierServiceUrl, Void.class, partId);
        } catch (HttpClientErrorException.NotFound e) {
            throw new PartNotFoundException();
        } catch (RestClientException e) {
            throw new RuntimeException("Ошибка при проверке инвентаря", e);
        }
    }

    public PartResponseDto getPartById(Long partId) {
        ResponseEntity<PartResponseDto> response = restTemplate.getForEntity(
                "http://part-service/api/parts/" + partId, PartResponseDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new PartNotFoundException();
        }

        return response.getBody();
    }

}
