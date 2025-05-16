package ru.fiarr4ik.categoryservice.controller;

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
import ru.fiarr4ik.categoryservice.dto.CategoryDto;
import ru.fiarr4ik.categoryservice.dto.ErrorResponseDto;
import ru.fiarr4ik.categoryservice.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Категории", description = "API для управления категориями")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(
            summary = "Получить все категории",
            description = "Возвращает список все категории в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список категорий успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))
            )
    })
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Получить категорию по ID.
     *
     * @param id Идентификатор категории
     * @return DTO категории
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить категорию по ID",
            description = "Возвращает информацию о категории по её уникальному идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Категория найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Категория не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<CategoryDto> getSupplierById(
            @Parameter(description = "ID категории", required = true)
            @PathVariable(name = "id") Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    /**
     * Создать новую категорию.
     *
     * @param categoryDto Данные новой категории
     * @return Созданный поставщик
     */
    @PostMapping
    @Operation(
            summary = "Создать новую категорию",
            description = "Создает новую категорию с указанными данными."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Категория успешно создана",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибки валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<CategoryDto> createSupplier(
            @Parameter(description = "Данные новой категории", required = true)
            @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto created = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Обновить данные категории.
     *
     * @param id Идентификатор категории
     * @param categoryDto Новые данные категории
     * @return Обновленная категория
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить данные категории",
            description = "Обновляет информацию о существующей категории."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Категория успешно обновлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибки валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Категория не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<CategoryDto> updateCategory(
            @Parameter(description = "ID категории", required = true)
            @PathVariable(name = "id") Long id,
            @Parameter(description = "Новые данные категории", required = true)
            @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updated = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Удалить категорию по ID.
     *
     * @param id Идентификатор категории
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить категорию",
            description = "Удаляет категорию по её идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Категория успешно удалена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Категория не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID категории", required = true)
            @PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
