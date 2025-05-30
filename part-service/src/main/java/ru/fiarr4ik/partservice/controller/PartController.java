package ru.fiarr4ik.partservice.controller;

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
import ru.fiarr4ik.partservice.dto.PartDto;
import ru.fiarr4ik.partservice.dto.PartResponseDto;
import ru.fiarr4ik.partservice.service.PartService;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
@Tag(name = "Запчасти", description = "API для управления запчастями")
public class PartController {

    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @PostMapping
    public ResponseEntity<PartDto> createPart(@Valid @RequestBody PartDto partDto) {
        PartDto createdPart = partService.createPart(partDto);
        return new ResponseEntity<>(createdPart, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PartResponseDto>> getParts() {
        List<PartResponseDto> list = partService.getAllParts();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartResponseDto> getPart(@PathVariable(name = "id") Long id) {
        PartResponseDto partResponseDto = partService.getPartById(id);
        return new ResponseEntity<>(partResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartDto> updatePart(@PathVariable(name = "id") Long id, @Valid @RequestBody PartDto partDto) {
        PartDto updated = partService.updatePart(id, partDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PartDto> deletePart(@PathVariable(name = "id") Long id) {
        partService.deletePart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Проверяет, существует ли хотя бы одна запчасть с указанной категорией.
     *
     * @param categoryId ID категории
     * @return true, если такая запчасть есть
     */
    @GetMapping("/exists-by-category/{categoryId}")
    public boolean existsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return partService.existsByCategoryId(categoryId);
    }

    @GetMapping("/exists-by-supplier/{supplierId}")
    public boolean existsBySupplierId(@PathVariable("supplierId") Long supplierId) {
        return partService.existsBySupplierId(supplierId);
    }

}
