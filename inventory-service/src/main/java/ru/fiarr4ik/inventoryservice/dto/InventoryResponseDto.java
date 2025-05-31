package ru.fiarr4ik.inventoryservice.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Data
public class InventoryResponseDto {

    private Long inventoryId;

    private PartResponseDto part;

    private Integer quantityInStock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date lastRestockDate;

}
