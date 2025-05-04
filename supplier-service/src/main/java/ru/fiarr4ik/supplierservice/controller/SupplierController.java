package ru.fiarr4ik.supplierservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fiarr4ik.supplierservice.dto.ErrorResponseDto;
import ru.fiarr4ik.supplierservice.dto.SupplierDto;
import ru.fiarr4ik.supplierservice.service.SupplierService;

import java.util.List;

/**
 * REST-контроллер для работы с поставщиками.
 * Предоставляет API для управления поставщиками, включая создание, чтение, обновление и удаление.
 */
@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Поставщики", description = "API для управления поставщиками")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * Получить список всех поставщиков.
     *
     * @return Список поставщиков
     */
    @GetMapping
    @Operation(
            summary = "Получить всех поставщиков",
            description = "Возвращает список всех поставщиков в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список поставщиков успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))
            )
    })
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        List<SupplierDto> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    /**
     * Получить поставщика по ID.
     *
     * @param id Идентификатор поставщика
     * @return DTO поставщика
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить поставщика по ID",
            description = "Возвращает информацию о поставщике по его уникальному идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Поставщик найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SupplierDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Поставщик не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<SupplierDto> getSupplierById(
            @Parameter(description = "ID поставщика", required = true)
            @PathVariable Long id) {
        SupplierDto supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    /**
     * Создать нового поставщика.
     *
     * @param supplierDto Данные нового поставщика
     * @return Созданный поставщик
     */
    @PostMapping
    @Operation(
            summary = "Создать нового поставщика",
            description = "Создает нового поставщика с указанными данными. " +
                    "Email и телефон должны быть уникальными."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Поставщик успешно создан",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SupplierDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибки валидации или дублирования данных",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<SupplierDto> createSupplier(
            @Parameter(description = "Данные нового поставщика", required = true)
            @Valid @RequestBody SupplierDto supplierDto) {
        SupplierDto created = supplierService.createSupplier(supplierDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Обновить данные поставщика.
     *
     * @param id Идентификатор поставщика
     * @param supplierDto Новые данные поставщика
     * @return Обновленный поставщик
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить данные поставщика",
            description = "Обновляет информацию о существующем поставщике. " +
                    "Email и телефон должны быть уникальными."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Поставщик успешно обновлен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SupplierDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибки валидации или дублирования данных",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Поставщик не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<SupplierDto> updateSupplier(
            @Parameter(description = "ID поставщика", required = true)
            @PathVariable Long id,
            @Parameter(description = "Новые данные поставщика", required = true)
            @Valid @RequestBody SupplierDto supplierDto) {
        SupplierDto updated = supplierService.updateSupplier(id, supplierDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Удалить поставщика по ID.
     *
     * @param id Идентификатор поставщика
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить поставщика",
            description = "Удаляет поставщика по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Поставщик успешно удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Поставщик не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<Void> deleteSupplier(
            @Parameter(description = "ID поставщика", required = true)
            @PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok().build();
    }

}
