package ru.fiarr4ik.supplierservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import ru.fiarr4ik.supplierservice.dto.SupplierDto;
import ru.fiarr4ik.supplierservice.service.SupplierService;

import java.util.List;

/**
 * REST-контроллер для работы с поставщиками.
 * Предоставляет CRUD операции над ресурсом "Поставщик".
 */
@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier API", description = "CRUD операции с поставщиками")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * Получает список всех поставщиков.
     *
     * @return ResponseEntity со списком {@link SupplierDto} и статусом 200 OK
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
     * Получает поставщика по его идентификатору.
     *
     * @param supplierId уникальный идентификатор поставщика
     * @return ResponseEntity с {@link SupplierDto} и статусом 200 OK, или сообщение об ошибке и статус 404 NOT FOUND
     */
    @GetMapping("/{supplierId}")
    @Operation(summary = "Получить поставщика по ID", description = "Возвращает одного поставщика по указанному ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставщик найден"),
            @ApiResponse(responseCode = "404", description = "Поставщик не найден", content = @Content)
    })
    public ResponseEntity<?> getSupplierById(@PathVariable Long supplierId) {
        SupplierDto supplier = supplierService.getSupplierById(supplierId);
        return ResponseEntity.ok(supplier);
    }

    /**
     * Создаёт нового поставщика на основе переданных данных.
     *
     * @param supplierDto данные нового поставщика
     * @return ResponseEntity с созданным {@link SupplierDto} и статусом 201 CREATED
     */
    @PostMapping
    @Operation(summary = "Создать нового поставщика", description = "Создает нового поставщика на основе переданных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Поставщик успешно создан")
    })
    public ResponseEntity<SupplierDto> createSupplier(@RequestBody @Valid SupplierDto supplierDto) {
        SupplierDto created = supplierService.createSupplier(supplierDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Обновляет информацию о существующем поставщике.
     *
     * @param supplierId идентификатор поставщика
     * @param supplierDto новые данные поставщика
     * @return ResponseEntity с обновлённым {@link SupplierDto} и статусом 200 OK, или сообщение об ошибке и статус 404 NOT FOUND
     */
    @PutMapping("/{supplierId}")
    @Operation(summary = "Обновить данные поставщика", description = "Обновляет информацию о поставщике по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставщик успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Поставщик не найден", content = @Content)
    })
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable Long supplierId, @RequestBody @Valid SupplierDto supplierDto) {
        SupplierDto updated = supplierService.updateSupplier(supplierId, supplierDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Удаляет поставщика по его идентификатору.
     *
     * @param supplierId идентификатор поставщика
     * @return ResponseEntity с удалённым {@link SupplierDto} и статусом 200 OK, или сообщение об ошибке и статус 404 NOT FOUND
     */
    @DeleteMapping("/{supplierId}")
    @Operation(summary = "Удалить поставщика по ID", description = "Удаляет поставщика по указанному ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставщик успешно удален"),
            @ApiResponse(responseCode = "404", description = "Поставщик не найден", content = @Content)
    })
    public ResponseEntity<SupplierDto> deleteSupplier(@PathVariable Long supplierId) {
        SupplierDto deleted = supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok(deleted);
    }

}
