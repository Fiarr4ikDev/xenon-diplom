package ru.fiarr4ik.inventoryservice.exception;

public class PartNotFoundException extends RuntimeException {

    public PartNotFoundException() {
        super("Запчасть не найдена");
    }

}
