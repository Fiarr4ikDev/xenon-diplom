package ru.fiarr4ik.inventoryservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartResponseDto {

    private String name;

    private String description;

    private CategoryDto category;

    private SupplierDto supplier;

    private Double unitPrice;

}
