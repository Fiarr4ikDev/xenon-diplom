package ru.fiarr4ik.partservice.controller;

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
import ru.fiarr4ik.partservice.dto.ErrorResponseDto;
import ru.fiarr4ik.partservice.dto.PartDto;
import ru.fiarr4ik.partservice.dto.PartResponseDto;
import ru.fiarr4ik.partservice.service.PartService;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Запчасти", description = "API для управления запчастями")
public class PartController {

    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @Operation(summary = "Создать новую запчасть")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Запчасть успешно создана",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PartDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибки валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<PartDto> createPart(@Valid @RequestBody PartDto partDto) {
        PartDto createdPart = partService.createPart(partDto);
        return new ResponseEntity<>(createdPart, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить список всех запчастей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список запчастей получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PartResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<PartResponseDto>> getParts() {
        List<PartResponseDto> list = partService.getAllParts();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "Получить запчасть по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запчасть найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PartResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Запчасть не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PartResponseDto> getPart(@PathVariable(name = "id") Long id) {
        PartResponseDto partResponseDto = partService.getPartById(id);
        return new ResponseEntity<>(partResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные запчасти")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запчасть успешно обновлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PartDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибки валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Запчасть не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PartDto> updatePart(@PathVariable(name = "id") Long id, @Valid @RequestBody PartDto partDto) {
        PartDto updated = partService.updatePart(id, partDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Удалить запчасть")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Запчасть успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Запчасть не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable(name = "id") Long id) {
        partService.deletePart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Проверить, существует ли запчасть с указанной категорией")
    @ApiResponse(responseCode = "200", description = "Возвращает true, если запчасть с данной категорией существует")
    @GetMapping("/exists-by-category/{categoryId}")
    public boolean existsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return partService.existsByCategoryId(categoryId);
    }

    @Operation(summary = "Проверить, существует ли запчасть с указанным поставщиком")
    @ApiResponse(responseCode = "200", description = "Возвращает true, если запчасть с данным поставщиком существует")
    @GetMapping("/exists-by-supplier/{supplierId}")
    public boolean existsBySupplierId(@PathVariable("supplierId") Long supplierId) {
        return partService.existsBySupplierId(supplierId);
    }

}
