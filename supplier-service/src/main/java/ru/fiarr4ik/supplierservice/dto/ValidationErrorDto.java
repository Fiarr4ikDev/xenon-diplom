package ru.fiarr4ik.supplierservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

/**
 * DTO для возврата ошибок валидации.
 */
@Getter
@Schema(description = "Ошибка валидации входных данных")
public class ValidationErrorDto {

    @Schema(description = "Список сообщений об ошибках валидации")
    private final List<String> messages;

    public ValidationErrorDto(List<String> messages) {
        this.messages = messages;
    }

}
