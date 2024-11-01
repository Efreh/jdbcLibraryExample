package com.efr.jdbcLibraryExample.exception;

// Исключение, которое выбрасывается, когда книга не найдена
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}