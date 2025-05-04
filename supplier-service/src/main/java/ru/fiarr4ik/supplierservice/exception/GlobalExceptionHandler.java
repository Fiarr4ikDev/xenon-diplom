package ru.fiarr4ik.supplierservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<String> handleSupplierNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Поставщик не найден");
    }

}
