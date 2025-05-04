package ru.fiarr4ik.supplierservice.controller;

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
import ru.fiarr4ik.supplierservice.dto.SupplierDto;
import ru.fiarr4ik.supplierservice.exception.SupplierNotFoundException;
import ru.fiarr4ik.supplierservice.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("supplier-service")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public ResponseEntity<List<SupplierDto>> getAll() {
        List<SupplierDto> list = supplierService.getAllSuppliers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/suppliers/{supplierId}")
    public ResponseEntity<?> getOne(@PathVariable Long supplierId) throws SupplierNotFoundException {
        try {
            SupplierDto supplierDto = supplierService.getSupplierById(supplierId);
            return new ResponseEntity<>(supplierDto, HttpStatus.OK);
        } catch (SupplierNotFoundException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/suppliers")
    public ResponseEntity<SupplierDto> create(SupplierDto supplierDto) {
        SupplierDto createdSupplierDto = supplierService.createSupplier(supplierDto);
        return new ResponseEntity<>(createdSupplierDto, HttpStatus.CREATED);
    }

    @PutMapping("/suppliers/{supplierId}")
    public ResponseEntity<?> update(@PathVariable Long supplierId, @RequestBody SupplierDto supplierDto) {
        try {
            SupplierDto createdSupplierDto = supplierService.updateSupplier(supplierId, supplierDto);
            return new ResponseEntity<>(createdSupplierDto, HttpStatus.OK);
        } catch (SupplierNotFoundException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/suppliers/{supplierId}")
    public ResponseEntity<?> delete(@PathVariable Long supplierId) {
        try {
            SupplierDto supplierDto = supplierService.getSupplierById(supplierId);
            supplierService.deleteSupplier(supplierId);
            return new ResponseEntity<>(supplierDto, HttpStatus.OK);
        } catch (SupplierNotFoundException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
