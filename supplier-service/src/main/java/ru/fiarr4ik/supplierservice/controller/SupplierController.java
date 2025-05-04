package ru.fiarr4ik.supplierservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
 */
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * Получить список всех поставщиков.
     *
     * @return Список {@link SupplierDto}
     */
    @GetMapping
    @Operation(summary = "Получить всех поставщиков", description = "Возвращает список всех поставщиков")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список поставщиков успешно получен")
    })
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        List<SupplierDto> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    /**
     * Получить поставщика по ID.
     *
     * @param id Идентификатор поставщика
     * @return {@link SupplierDto}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получить поставщика по ID", description = "Возвращает одного поставщика по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставщик найден"),
            @ApiResponse(responseCode = "404", description = "Поставщик не найден", content = @Content)
    })
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Long id) {
        SupplierDto supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    /**
     * Создать нового поставщика.
     *
     * @param supplierDto Данные нового поставщика
     * @return Созданный {@link SupplierDto} с кодом 201
     */
    @PostMapping
    @Operation(summary = "Создать нового поставщика", description = "Создает нового поставщика")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Поставщик успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибки валидации или дублирования данных",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<SupplierDto> createSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        SupplierDto created = supplierService.createSupplier(supplierDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Обновить данные поставщика.
     *
     * @param id Идентификатор поставщика
     * @param supplierDto Новые данные поставщика
     * @return Обновлённый {@link SupplierDto}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные поставщика", description = "Обновляет информацию о поставщике по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставщик успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Ошибки валидации или дублирования данных",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Поставщик не найден", content = @Content)
    })
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierDto supplierDto) {
        SupplierDto updated = supplierService.updateSupplier(id, supplierDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Удалить поставщика по ID.
     *
     * @param id Идентификатор поставщика
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить поставщика по ID", description = "Удаляет поставщика по указанному ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставщик успешно удален"),
            @ApiResponse(responseCode = "404", description = "Поставщик не найден", content = @Content)
    })
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok().build();
    }

}
