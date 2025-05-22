package ru.fiarr4ik.partservice.exception;

public class PartNotFoundException extends RuntimeException {
    public PartNotFoundException() {
        super("Запчасть не найдена");
    }
}
