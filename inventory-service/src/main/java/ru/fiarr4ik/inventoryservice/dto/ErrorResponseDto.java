package ru.fiarr4ik.inventoryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

/**
 * DTO для возврата ошибок валидации.
 */
@Getter
@Schema(description = "Ошибка валидации")
public class ErrorResponseDto {

    @Schema(description = "Список сообщений об ошибках")
    private final List<String> messages;

    public ErrorResponseDto(List<String> messages) {
        this.messages = messages;
    }

}
