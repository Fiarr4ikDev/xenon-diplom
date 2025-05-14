package ru.fiarr4ik.partservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.fiarr4ik.partservice.dto.ErrorResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PartNotFoundException.class)
    public ResponseEntity<String> handleSupplierNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Запчасть не найдена");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponseDto(errors), HttpStatus.BAD_REQUEST);
    }

}