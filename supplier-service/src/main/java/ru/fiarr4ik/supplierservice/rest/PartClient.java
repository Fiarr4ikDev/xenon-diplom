package ru.fiarr4ik.supplierservice.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "part-service", url = "http://localhost:8083")
public interface PartClient {

    /**
     * Проверить, есть ли запчасти с указанным поставщиком
     * @param supplierId ID поставщика
     * @return true, если есть
     */
    @GetMapping("/api/parts/exists-by-supplier/{supplierId}")
    boolean existsBySupplierId(@PathVariable("supplierId") Long supplierId);

}
