package ru.fiarr4ik.partservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.partservice.dto.CategoryDto;
import ru.fiarr4ik.partservice.dto.PartDto;
import ru.fiarr4ik.partservice.dto.PartResponseDto;
import ru.fiarr4ik.partservice.dto.SupplierDto;
import ru.fiarr4ik.partservice.entity.Part;
import ru.fiarr4ik.partservice.exception.PartNotFoundException;
import ru.fiarr4ik.partservice.repository.PartRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return parts.stream().map(this::getPartResponseDto).collect(Collectors.toList());
    }

    public PartResponseDto getPartById(Long id) {
        Optional<Part> part = partRepository.findById(id);
        return part.map(this::getPartResponseDto).orElse(null);
    }

    public PartDto updatePart(Long id, PartDto partDto) {
        Optional<Part> part = partRepository.findPartByPartId(id);
        if (part.isPresent()) {
            Part partToUpdate = part.get();
            partToUpdate.setName(partDto.getName());
            partToUpdate.setDescription(partDto.getDescription());
            partToUpdate.setCategoryId(partDto.getCategoryId());
            partToUpdate.setSupplierId(partToUpdate.getSupplierId());
            partToUpdate.setUnitPrice(partDto.getUnitPrice());
            partRepository.save(partToUpdate);
            validateSupplierService.validateSupplierExists(partDto.getSupplierId());
            validationCategoryService.validateCategoryExists(partDto.getCategoryId());
            partRepository.save(partToUpdate);
        }
        return null;
    }

    public void deletePart(Long id) {
        Optional<Part> part = partRepository.findPartByPartId(id);
        if (part.isPresent()) {
            partRepository.delete(part.get());
        } else {
            throw new PartNotFoundException();
        }
    }

    private PartResponseDto getPartResponseDto(Part part) {
        CategoryDto categoryDto = validationCategoryService.getCategoryById(part.getCategoryId());
        SupplierDto supplierDto = validateSupplierService.getSupplierById(part.getSupplierId());
        PartResponseDto responseDto = new PartResponseDto();
        responseDto.setName(part.getName());
        responseDto.setDescription(part.getDescription());
        responseDto.setCategory(categoryDto);
        responseDto.setSupplier(supplierDto);
        responseDto.setUnitPrice(part.getUnitPrice());
        return responseDto;
    }

    /**
     * Проверяет, существует ли хотя бы одна запчасть с указанной категорией.
     *
     * @param categoryId ID категории
     * @return true, если такая запчасть есть
     */
    public boolean existsByCategoryId(Long categoryId) {
        return partRepository.existsByCategoryId(categoryId);
    }

    /**
     * Проверяет, существует ли хотя бы одна запчасть с указанным поставщиком.
     *
     * @param supplierId ID поставщика
     * @return true, если такая запчасть есть
     */
    public boolean existsBySupplierId(Long supplierId) {
        return partRepository.existsBySupplierId(supplierId);
    }

}
