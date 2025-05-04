package ru.fiarr4ik.supplierservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.supplierservice.dto.SupplierDto;
import ru.fiarr4ik.supplierservice.entity.Supplier;
import ru.fiarr4ik.supplierservice.exception.SupplierNotFoundException;
import ru.fiarr4ik.supplierservice.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMappingService supplierMappingService;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, SupplierMappingService supplierMappingService) {
        this.supplierRepository = supplierRepository;
        this.supplierMappingService = supplierMappingService;
    }

    public SupplierDto createSupplier(SupplierDto supplierDto) {
        Supplier supplier = supplierMappingService.toEntity(supplierDto);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMappingService.toDto(savedSupplier);
    }

    public SupplierDto getSupplierById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(id);
        if (supplier.isPresent()) {
            return supplierMappingService.toDto(supplier.get());
        } else {
            throw new SupplierNotFoundException("Поставщик c id " + id + " не найден");
        }
    }

    public List<SupplierDto> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(supplierMappingService::toDto)
                .collect(Collectors.toList());
    }

    public SupplierDto updateSupplier(Long id, SupplierDto supplierDto) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(id);
        if (supplier.isPresent()) {
            Supplier supplierToUpdate = supplierMappingService.toEntity(supplierDto);
            Supplier foundedSupplier = supplier.get();
            foundedSupplier.setName(supplierToUpdate.getName());
            foundedSupplier.setContactPerson(supplierToUpdate.getContactPerson());
            foundedSupplier.setAddress(supplierToUpdate.getAddress());
            foundedSupplier.setPhone(supplierToUpdate.getPhone());
            foundedSupplier.setEmail(supplierToUpdate.getEmail());
            Supplier savedSupplier = supplierRepository.save(foundedSupplier);
            return supplierMappingService.toDto(savedSupplier);
        } else {
            throw new SupplierNotFoundException("Поставщик c id " + id + " не найден");
        }
    }

    public SupplierDto deleteSupplier(Long id) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(id);
        if (supplier.isPresent()) {
            supplierRepository.delete(supplier.get());
        } else {
            throw new SupplierNotFoundException("Поставщик c id " + id + " не найден");
        }
        return null;
    }

}
