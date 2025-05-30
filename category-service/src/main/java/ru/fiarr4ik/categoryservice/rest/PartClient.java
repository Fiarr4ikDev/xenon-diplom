package ru.fiarr4ik.categoryservice.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "part-service", url = "http://localhost:8083")
public interface PartClient {

    /**
     * Проверяет, используется ли категория с указанным ID.
     *
     * @param categoryId ID категории
     * @return true, если есть хотя бы одна запчасть с этой категорией
     */
    @GetMapping("/api/parts/exists-by-category/{categoryId}")
    boolean existsByCategory(@PathVariable("categoryId") Long categoryId);

}
