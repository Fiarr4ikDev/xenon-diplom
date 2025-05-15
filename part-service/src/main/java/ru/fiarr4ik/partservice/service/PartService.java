package ru.fiarr4ik.partservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.partservice.dto.CategoryDto;
import ru.fiarr4ik.partservice.dto.PartDto;
import ru.fiarr4ik.partservice.dto.PartResponseDto;
import ru.fiarr4ik.partservice.dto.SupplierDto;
import ru.fiarr4ik.partservice.entity.Part;
import ru.fiarr4ik.partservice.repository.PartRepository;

import java.util.List;

@Service
public class PartService {

    private final PartRepository partRepository;
    private final PartMapperService partMapperService;
    private final ValidateSupplierService validateSupplierService;
    private final ValidationCategoryService validationCategoryService;

    @Autowired
    public PartService(PartRepository partRepository, PartMapperService partMapperService, ValidateSupplierService validateSupplierService, ValidationCategoryService validationCategoryService) {
        this.partRepository = partRepository;
        this.partMapperService = partMapperService;
        this.validateSupplierService = validateSupplierService;
        this.validationCategoryService = validationCategoryService;
    }

    public PartDto createPart(PartDto partDto) {
        validateSupplierService.validateSupplierExists(partDto.getSupplierId());
        validationCategoryService.validateCategoryExists(partDto.getCategoryId());
        Part part = partMapperService.toModel(partDto);
        Part savedPart = partRepository.save(part);
        return partMapperService.toDto(savedPart);
    }

    public List<PartResponseDto> getAllParts() {
        List<Part> parts = partRepository.findAll();

        return parts.stream().map(part -> {
            CategoryDto categoryDto = validationCategoryService.getCategoryById(part.getCategoryId());
            SupplierDto supplierDto = validateSupplierService.getSupplierById(part.getSupplierId());

            PartResponseDto responseDto = new PartResponseDto();
            responseDto.setName(part.getName());
            responseDto.setDescription(part.getDescription());
            responseDto.setCategory(categoryDto);
            responseDto.setSupplier(supplierDto);
            responseDto.setUnitPrice(part.getUnitPrice());

            return responseDto;
        }).toList();
    }
}
