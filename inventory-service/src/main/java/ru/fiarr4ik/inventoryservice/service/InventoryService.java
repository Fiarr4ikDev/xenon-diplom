package ru.fiarr4ik.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.inventoryservice.dto.InventoryRequestDto;
import ru.fiarr4ik.inventoryservice.dto.InventoryResponseDto;
import ru.fiarr4ik.inventoryservice.dto.InventoryUpdateRequestDto;
import ru.fiarr4ik.inventoryservice.dto.PartResponseDto;
import ru.fiarr4ik.inventoryservice.entity.Inventory;
import ru.fiarr4ik.inventoryservice.exception.InventoryNotFoundException;
import ru.fiarr4ik.inventoryservice.repository.InventoryRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMappingService inventoryMappingService;
    private final ValidatePartService validatePartService;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, InventoryMappingService inventoryMappingService, ValidatePartService validatePartService) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMappingService = inventoryMappingService;
        this.validatePartService = validatePartService;
    }

    public InventoryResponseDto createInventory(InventoryRequestDto requestDto) {
        validatePartService.validatePartExist(requestDto.getPartId());
        Inventory inventory = inventoryMappingService.toEntity(requestDto);
        inventory.setLastRestockDate(new Date());
        Inventory savedInventory = inventoryRepository.save(inventory);

        PartResponseDto partResponseDto = validatePartService.getPartById(savedInventory.getPartId());

        InventoryResponseDto responseDto = inventoryMappingService.toResponseDto(savedInventory);
        responseDto.setPart(partResponseDto);
        return responseDto;
    }

    public InventoryResponseDto getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Инвентарь с id=" + id + " не найден!"));

        PartResponseDto partResponseDto = validatePartService.getPartById(inventory.getPartId());

        InventoryResponseDto responseDto = inventoryMappingService.toResponseDto(inventory);
        responseDto.setPart(partResponseDto);
        return responseDto;
    }

    public List<InventoryResponseDto> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(inventory -> {
                    InventoryResponseDto dto = inventoryMappingService.toResponseDto(inventory);
                    PartResponseDto partResponseDto = validatePartService.getPartById(inventory.getPartId());
                    dto.setPart(partResponseDto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public InventoryResponseDto updateInventory(Long id, InventoryUpdateRequestDto requestDto) {
        Inventory inventoryToUpdate = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Инвентарь с id=" + id + " не найден!"));

        if (requestDto.getQuantityInStock() != null) {
            inventoryToUpdate.setQuantityInStock(requestDto.getQuantityInStock());
        }

        inventoryToUpdate.setLastRestockDate(new Date());

        Inventory savedInventory = inventoryRepository.save(inventoryToUpdate);

        PartResponseDto partResponseDto = validatePartService.getPartById(savedInventory.getPartId());
        InventoryResponseDto responseDto = inventoryMappingService.toResponseDto(savedInventory);
        responseDto.setPart(partResponseDto);
        return responseDto;
    }

    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Инвентарь с id=" + id + " не найден!"));
        inventoryRepository.delete(inventory);
    }

}
