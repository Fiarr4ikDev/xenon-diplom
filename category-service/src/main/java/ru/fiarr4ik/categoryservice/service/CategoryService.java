package ru.fiarr4ik.categoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.categoryservice.dto.CategoryDto;
import ru.fiarr4ik.categoryservice.entity.Category;
import ru.fiarr4ik.categoryservice.exception.CategoryNotFoundException;
import ru.fiarr4ik.categoryservice.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMappingService categoryMappingService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMappingService categoryMappingService) {
        this.categoryRepository = categoryRepository;
        this.categoryMappingService = categoryMappingService;
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMappingService.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMappingService.toDto(savedCategory);
    }

    public CategoryDto getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return categoryMappingService.toDto(category.get());
        } else {
            throw new CategoryNotFoundException();
        }
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMappingService::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category categoryToUpdate = category.get();
            categoryToUpdate.setName(categoryDto.getName());
            categoryToUpdate.setDescription(categoryDto.getDescription());
            Category savedCategory = categoryRepository.save(categoryToUpdate);
            return categoryMappingService.toDto(savedCategory);
        } else {
            throw new CategoryNotFoundException();
        }
    }

    public void deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
        } else {
            throw new CategoryNotFoundException();
        }
    }



}
