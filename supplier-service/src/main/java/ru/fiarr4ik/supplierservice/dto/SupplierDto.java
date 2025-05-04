package ru.fiarr4ik.supplierservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for {@link ru.fiarr4ik.supplierservice.entity.Supplier}
 */
@Getter
@Setter
@Schema(description = "DTO сущности поставщика")
public class SupplierDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String contactPerson;

    @JsonProperty
    private String phone;

    @JsonProperty
    private String email;

    @JsonProperty
    private String address;

}
