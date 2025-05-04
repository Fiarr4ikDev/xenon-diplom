package ru.fiarr4ik.supplierservice.exception;

public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(String message) {
        super(message);
    }

}
