package ru.fiarr4ik.inventoryservice.controller;

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
import ru.fiarr4ik.inventoryservice.dto.ErrorResponseDto;
import ru.fiarr4ik.inventoryservice.dto.InventoryRequestDto;
import ru.fiarr4ik.inventoryservice.dto.InventoryResponseDto;
import ru.fiarr4ik.inventoryservice.dto.InventoryUpdateRequestDto;
import ru.fiarr4ik.inventoryservice.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@Tag(name = "Инвентарь", description = "API для управления складскими запасами")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @Operation(
            summary = "Получить все позиции инвентаря",
            description = "Возвращает список всех позиций на складе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список позиций успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))
            )
    })
    public ResponseEntity<List<InventoryResponseDto>> getAllInventories() {
        List<InventoryResponseDto> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить запись инвентаря по ID",
            description = "Возвращает информацию об инвентаре по его уникальному идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Инвентарь найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Инвентарь не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<InventoryResponseDto> getInventoryById(
            @Parameter(description = "ID инвентаря", required = true)
            @PathVariable(name = "id") Long id) {
        InventoryResponseDto inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    @PostMapping
    @Operation(
            summary = "Создать новую запись инвентаря",
            description = "Создает новую запись с указанными данными"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Запись успешно создана",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибки валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<InventoryResponseDto> createInventory(
            @Parameter(description = "Данные новой записи", required = true)
            @Valid @RequestBody InventoryRequestDto requestDto) {
        InventoryResponseDto created = inventoryService.createInventory(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить данные инвентаря",
            description = "Обновляет информацию о существующей записи"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запись успешно обновлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибки валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<InventoryResponseDto> updateInventory(
            @Parameter(description = "ID инвентаря", required = true)
            @PathVariable(name = "id") Long id,
            @Parameter(description = "Новые данные инвентаря", required = true)
            @Valid @RequestBody InventoryUpdateRequestDto requestDto) {
        InventoryResponseDto updated = inventoryService.updateInventory(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить запись инвентаря",
            description = "Удаляет запись по её уникальному идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запись успешно удалена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<Void> deleteInventory(
            @Parameter(description = "ID инвентаря", required = true)
            @PathVariable(name = "id") Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok().build();
    }

}
