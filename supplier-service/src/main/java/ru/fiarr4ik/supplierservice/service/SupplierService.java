package ru.fiarr4ik.supplierservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.supplierservice.dto.SupplierDto;
import ru.fiarr4ik.supplierservice.entity.Supplier;
import ru.fiarr4ik.supplierservice.exception.SupplierNotFoundException;
import ru.fiarr4ik.supplierservice.exception.UniqueConstraintViolationException;
import ru.fiarr4ik.supplierservice.repository.SupplierRepository;
import ru.fiarr4ik.supplierservice.rest.PartClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMappingService supplierMappingService;
    private final PartClient partClient;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, SupplierMappingService supplierMappingService, PartClient partClient) {
        this.supplierRepository = supplierRepository;
        this.supplierMappingService = supplierMappingService;
        this.partClient = partClient;
    }

    /**
     * Создает нового поставщика.
     *
     * @param supplierDto данные нового поставщика
     * @return DTO созданного поставщика
     * @throws UniqueConstraintViolationException если телефон или email уже заняты
     */
    public SupplierDto createSupplier(SupplierDto supplierDto) {
        validateUniqueFields(supplierDto);
        Supplier supplier = supplierMappingService.toEntity(supplierDto);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMappingService.toDto(savedSupplier);
    }

    /**
     * Получает поставщика по его ID.
     *
     * @param id идентификатор поставщика
     * @return DTO поставщика
     * @throws SupplierNotFoundException если поставщик не найден
     */
    public SupplierDto getSupplierById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(id);
        if (supplier.isPresent()) {
            return supplierMappingService.toDto(supplier.get());
        } else {
            throw new SupplierNotFoundException("Поставщик c id " + id + " не найден");
        }
    }

    /**
     * Возвращает список всех поставщиков.
     *
     * @return список DTO поставщиков
     */
    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplierMappingService::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Обновляет информацию о поставщике.
     *
     * @param id          идентификатор поставщика
     * @param supplierDto новые данные поставщика
     * @return DTO обновленного поставщика
     * @throws SupplierNotFoundException       если поставщик не найден
     * @throws UniqueConstraintViolationException если телефон или email уже заняты
     */
    public SupplierDto updateSupplier(Long id, SupplierDto supplierDto) {
        validateUniqueFields(supplierDto);

        Optional<Supplier> supplier = supplierRepository.findBySupplierId(id);
        if (supplier.isPresent()) {
            Supplier supplierToUpdate = supplier.get();
            supplierToUpdate.setName(supplierDto.getName());
            supplierToUpdate.setContactPerson(supplierDto.getContactPerson());
            supplierToUpdate.setAddress(supplierDto.getAddress());
            supplierToUpdate.setPhone(supplierDto.getPhone());
            supplierToUpdate.setEmail(supplierDto.getEmail());
            Supplier savedSupplier = supplierRepository.save(supplierToUpdate);
            return supplierMappingService.toDto(savedSupplier);
        } else {
            throw new SupplierNotFoundException("Поставщик c id " + id + " не найден");
        }
    }

    public void deleteSupplier(Long supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new SupplierNotFoundException("Поставщик c id " + supplierId + " не найден");
        }

        boolean hasParts = partClient.existsBySupplierId(supplierId);
        if (hasParts) {
            throw new IllegalStateException("Поставщик используется в запчастях и не может быть удалён");
        }

        supplierRepository.deleteById(supplierId);
    }

    /**
     * Проверяет уникальность поля phone и email.
     *
     * @param supplierDto данные поставщика
     * @throws UniqueConstraintViolationException если телефон или email уже заняты
     */
    private void validateUniqueFields(SupplierDto supplierDto) {
        if (supplierRepository.existsByPhone(supplierDto.getPhone())) {
            throw new UniqueConstraintViolationException("Телефон уже занят");
        }
        if (supplierRepository.existsByEmail(supplierDto.getEmail())) {
            throw new UniqueConstraintViolationException("Email уже занят");
        }
    }

}
