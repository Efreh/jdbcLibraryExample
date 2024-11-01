package com.efr.jdbcLibraryExample.repository;

import com.efr.jdbcLibraryExample.model.Book;

import java.util.List;
import java.util.Optional;

// Интерфейс репозитория для работы с книгами
public interface BookRepository {

    // Метод для получения списка всех книг
    List<Book> findAll();

    // Метод для поиска книги по идентификатору
    Optional<Book> findById(Long id);

    // Метод для сохранения новой книги в репозитории
    Book save(Book book);

    // Метод для обновления существующей книги по идентификатору
    Book update(Long id, Book book);

    // Метод для удаления книги по идентификатору
    void deleteById(Long id);
}