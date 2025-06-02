package ru.fiarr4ik.categoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для передачи данных о категории между слоями приложения.
 */
@Getter
@Setter
@Schema(description = "DTO модели категории с валидацией")
public class CategoryRequestDto {

    @JsonProperty("name")
    @Schema(description = "Название категории", example = "Расходные материалы")
    @NotEmpty(message = "Название категории не может быть пустым")
    private String name;

    @JsonProperty("description")
    @Schema(description = "Описание категории")
    private String description;

}
