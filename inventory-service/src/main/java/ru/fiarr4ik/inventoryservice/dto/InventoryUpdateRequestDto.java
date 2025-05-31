package ru.fiarr4ik.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateRequestDto {

    @Min(value = 0, message = "Количество не может быть отрицательным")
    @NotNull(message = "Количество должно быть указано")
    private Integer quantityInStock;

}
