package ru.fiarr4ik.supplierservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.supplierservice.dto.SupplierDto;
import ru.fiarr4ik.supplierservice.entity.Supplier;

/**
 * Сервис для конвертации сущностей в DTO и наоборот.
 * <p>Для конвертации используется {@link org.modelmapper.ModelMapper}.</p>
 */
@Service
public class SupplierMappingService {

    private final ModelMapper modelMapper;

    @Autowired
    public SupplierMappingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SupplierDto toDto(Supplier supplier) {
        return modelMapper.map(supplier, SupplierDto.class);
    }

    public Supplier toEntity(SupplierDto dto) {
        return modelMapper.map(dto, Supplier.class);
    }

}
