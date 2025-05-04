package ru.fiarr4ik.supplierservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для передачи данных о поставщике между слоями приложения.
 */
@Getter
@Setter
@Schema(description = "DTO модели поставщика с валидацией")
public class SupplierDto {

    @JsonProperty("name")
    @Schema(description = "Название компании поставщика", example = "ООО Поставка")
    @NotBlank(message = "Название компании не может быть пустым")
    private String name;

    @JsonProperty("contactPerson")
    @Schema(description = "ФИО контактного лица", example = "Иванов Иван Иванович")
    private String contactPerson;

    @JsonProperty("phone")
    @Schema(description = "Контактный телефон", example = "+79991234567")
    @NotBlank(message = "Телефон не может быть пустым")
    @Pattern(
            regexp = "^\\+?[0-9]{8,15}$",
            message = "Некорректный формат телефона. Пример: +79991234567"
    )
    private String phone;

    @JsonProperty("email")
    @Schema(description = "Электронная почта поставщика", example = "supplier@example.com")
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть в корректном формате")
    private String email;

    @JsonProperty("address")
    @Schema(description = "Адрес поставщика", example = "ул. Ленина, д. 10, г. Москва")
    private String address;

}
