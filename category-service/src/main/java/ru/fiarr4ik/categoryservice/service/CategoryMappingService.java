package ru.fiarr4ik.categoryservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.categoryservice.dto.CategoryDto;
import ru.fiarr4ik.categoryservice.entity.Category;

@Service
public class CategoryMappingService {

    private final ModelMapper modelMapper;

    @Autowired
    public CategoryMappingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDto toDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category toEntity(CategoryDto dto) {
        return modelMapper.map(dto, Category.class);
    }

}
