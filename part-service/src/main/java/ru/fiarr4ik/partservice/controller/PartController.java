package ru.fiarr4ik.partservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}