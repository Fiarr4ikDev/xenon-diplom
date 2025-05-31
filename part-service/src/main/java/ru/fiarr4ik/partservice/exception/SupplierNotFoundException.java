package ru.fiarr4ik.partservice.exception;

public class SupplierNotFoundException extends RuntimeException {

    public SupplierNotFoundException(Long supplierId) {
        super("Поставщик с ID " + supplierId + " не найден");
    }

}
