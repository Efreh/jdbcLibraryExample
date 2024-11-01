package com.efr.jdbcLibraryExample.service.interfaces;

import com.efr.jdbcLibraryExample.model.Book;

import java.util.List;
import java.util.Optional;

// Интерфейс для сервиса работы с книгами
public interface BookService {

    // Получение списка всех книг
    List<Book> getAllBooks();

    // Получение книги по идентификатору
    Optional<Book> getBookById(Long id);

    // Создание новой книги
    Book createBook(Book book);

    // Обновление существующей книги по идентификатору
    Book updateBook(Long id, Book bookDetails);

    // Удаление книги по идентификатору
    void deleteBook(Long id);
}