package ru.fiarr4ik.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequestDto {

    @NotNull(message = "partId не может быть null")
    private Long partId;

    @NotNull(message = "quantityInStock не может быть null")
    @Min(value = 1, message = "quantityInStock должен быть 0 или больше")
    private Integer quantityInStock;

}
