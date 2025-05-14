package ru.fiarr4ik.partservice.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long categoryId) {
        super("Категория с ID " + categoryId + " не найдена");
    }
}
