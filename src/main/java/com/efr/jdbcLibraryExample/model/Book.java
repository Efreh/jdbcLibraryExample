package com.efr.jdbcLibraryExample.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

// Класс для представления книги в библиотеке
@Getter
@Setter
@AllArgsConstructor
@Table("Book") // Указывает, что эта сущность будет отображена в таблице "Book" в базе данных
public class Book {

    @Id // Указывает, что это поле является идентификатором (primary key)
    private Long bookId; // Уникальный идентификатор книги

    private String title; // Название книги

    private String author; // Автор книги

    private int publicationYear; // Год публикации книги
}