package ru.fiarr4ik.inventoryservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.inventoryservice.dto.InventoryRequestDto;
import ru.fiarr4ik.inventoryservice.dto.InventoryResponseDto;
import ru.fiarr4ik.inventoryservice.entity.Inventory;

@Service
public class InventoryMappingService {

    private final ModelMapper modelMapper;

    @Autowired
    public InventoryMappingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public InventoryResponseDto toResponseDto(Inventory entity) {
        return modelMapper.map(entity, InventoryResponseDto.class);
    }

    public Inventory toEntity(InventoryRequestDto dto) {
        return modelMapper.map(dto, Inventory.class);
    }

}
