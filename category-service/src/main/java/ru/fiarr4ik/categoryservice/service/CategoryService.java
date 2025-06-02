package ru.fiarr4ik.categoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.categoryservice.dto.CategoryRequestDto;
import ru.fiarr4ik.categoryservice.dto.CategoryResponseDTO;
import ru.fiarr4ik.categoryservice.entity.Category;
import ru.fiarr4ik.categoryservice.exception.CategoryNotFoundException;
import ru.fiarr4ik.categoryservice.repository.CategoryRepository;
import ru.fiarr4ik.categoryservice.rest.PartClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMappingService categoryMappingService;
    private final PartClient partClient;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMappingService categoryMappingService, PartClient partClient) {
        this.categoryRepository = categoryRepository;
        this.categoryMappingService = categoryMappingService;
        this.partClient = partClient;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMappingService.toEntity(categoryRequestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMappingService.toDto(savedCategory);
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return categoryMappingService.toDto(category.get());
        } else {
            throw new CategoryNotFoundException();
        }
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMappingService::toDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category categoryToUpdate = category.get();
            categoryToUpdate.setName(categoryRequestDto.getName());
            categoryToUpdate.setDescription(categoryRequestDto.getDescription());
            Category savedCategory = categoryRepository.save(categoryToUpdate);
            return categoryMappingService.toDto(savedCategory);
        } else {
            throw new CategoryNotFoundException();
        }
    }

    /**
     * Удаляет категорию, если она не используется ни в одной запчасти.
     *
     * @param id ID категории
     */
    public void deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        if (partClient.existsByCategory(id)) {
            throw new IllegalStateException("Категория используется в запчастях и не может быть удалена");
        }

        categoryRepository.delete(category.get());
    }

}
