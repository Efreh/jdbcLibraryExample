package com.efr.jdbcLibraryExample.exception;

import lombok.Getter;
import lombok.Setter;

// Класс для представления структуры ответа об ошибке
@Getter
@Setter
public class ErrorResponse {
    private String error;
    private String message;

    // Конструктор, принимающий тип ошибки и сообщение об ошибке
    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }
}