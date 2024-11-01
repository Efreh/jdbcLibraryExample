package com.efr.jdbcLibraryExample.controller;

import com.efr.jdbcLibraryExample.exception.BookNotFoundException;
import com.efr.jdbcLibraryExample.model.Book;
import com.efr.jdbcLibraryExample.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Указывает, что данный класс является контроллером REST
@RequestMapping("/api/books") // Определяет базовый URL для всех методов в этом контроллере
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Метод для получения списка всех книг
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks()); // Возвращает список книг с кодом 200 OK
    }

    // Метод для получения книги по её идентификатору
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        // Получаем книгу по идентификатору; если не найдена, выбрасываем исключение
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с id " + id + " не найдена"));
        return ResponseEntity.ok(book); // Возвращает найденную книгу с кодом 200 OK
    }

    // Метод для создания новой книги
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book); // Создаём книгу через сервис
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook); // Возвращает созданную книгу с кодом 201 CREATED
    }

    // Метод для обновления информации о книге по её идентификатору
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book); // Обновляем книгу через сервис
        return ResponseEntity.ok(updatedBook); // Возвращает обновлённую книгу с кодом 200 OK
    }

    // Метод для удаления книги по её идентификатору
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id); // Удаляем книгу через сервис
        return ResponseEntity.noContent().build(); // Возвращает код 204 No Content, указывая на успешное удаление
    }
}