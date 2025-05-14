package ru.fiarr4ik.partservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.partservice.dto.PartDto;
import ru.fiarr4ik.partservice.entity.Part;
import ru.fiarr4ik.partservice.repository.PartRepository;

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
}
