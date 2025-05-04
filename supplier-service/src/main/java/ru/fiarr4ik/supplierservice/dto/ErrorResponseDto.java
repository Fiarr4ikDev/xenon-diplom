package ru.fiarr4ik.supplierservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

/**
 * DTO для возврата ошибок валидации и уникальности.
 */
@Getter
@Schema(description = "Ошибка валидации или нарушения уникальности")
public class ErrorResponseDto {

    @Schema(description = "Список сообщений об ошибках", example = "[\"Название компании не может быть пустым\", \"Email уже занят\"]")
    private final List<String> messages;

    public ErrorResponseDto(List<String> messages) {
        this.messages = messages;
    }

}
