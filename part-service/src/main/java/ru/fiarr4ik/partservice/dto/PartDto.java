package ru.fiarr4ik.partservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO модели запчасти с валидацией")
public class PartDto {

    @NotBlank(message = "Название запчасти не может быть пустым")
    @JsonProperty("name")
    @Schema(description = "Название запчасти", example = "Печатная плата кондиционера")
    private String name;

    @JsonProperty("description")
    @Schema(description = "Описание запчасти", example = "Электронная плата управления климат-контролем")
    private String description;

    @JsonProperty("categoryId")
    @Schema(description = "Идентификатор категории, к которой относится запчасть", example = "101")
    private Long categoryId;

    @JsonProperty("supplierId")
    @Schema(description = "Идентификатор поставщика запчасти", example = "205")
    private Long supplierId;

    @NotNull(message = "Цена за единицу запчасти обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть больше нуля")
    @JsonProperty("unitPrice")
    @Schema(description = "Цена за одну единицу запчасти", example = "1250.99")
    private Double unitPrice;
}
