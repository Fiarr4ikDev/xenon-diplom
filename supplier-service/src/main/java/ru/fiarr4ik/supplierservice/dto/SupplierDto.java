package ru.fiarr4ik.supplierservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для передачи данных о поставщике между слоями приложения.
 */
@Getter
@Setter
@Schema(description = "DTO модели поставщика")
public class SupplierDto {

    @JsonProperty("name")
    @Schema(description = "Название компании поставщика", example = "ООО Поставка")
    private String name;

    @JsonProperty("contactPerson")
    @Schema(description = "ФИО контактного лица", example = "Иванов Иван Иванович")
    private String contactPerson;

    @JsonProperty("phone")
    @Schema(description = "Контактный телефон", example = "+7 999 123-45-67")
    private String phone;

    @JsonProperty("email")
    @Schema(description = "Электронная почта поставщика", example = "supplier@example.com")
    private String email;

    @JsonProperty("address")
    @Schema(description = "Адрес поставщика", example = "ул. Ленина, д. 10, г. Москва")
    private String address;

}
