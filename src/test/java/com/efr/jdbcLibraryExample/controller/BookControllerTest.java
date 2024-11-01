package com.efr.jdbcLibraryExample.controller;

import com.efr.jdbcLibraryExample.exception.BookNotFoundException;
import com.efr.jdbcLibraryExample.model.Book;
import com.efr.jdbcLibraryExample.service.interfaces.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Тестовый класс для BookController
class BookControllerTest {

    @Mock
    private BookService bookService; // Мок-сервис для работы с книгами

    @InjectMocks
    private BookController bookController; // Контроллер, который мы тестируем

    private Book book1; // Первая книга для тестов
    private Book book2; // Вторая книга для тестов

    // Метод, который выполняется перед каждым тестом
    @BeforeEach
    void setUp() {
        // Инициализация моков
        MockitoAnnotations.openMocks(this);
        book1 = new Book(1L, "1984", "George Orwell", 1949); // Инициализация первой книги
        book2 = new Book(2L, "Brave New World", "Aldous Huxley", 1932); // Инициализация второй книги
    }

    @Test
    void getAllBooks_ReturnsListOfBooks() {
        // Настройка поведения мок-сервиса
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        // Вызов метода контроллера
        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        // Проверка ожидаемого результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(bookService, times(1)).getAllBooks(); // Проверка, что метод был вызван один раз
    }

    @Test
    void getBookById_ReturnsBook_WhenExists() {
        // Настройка поведения мок-сервиса
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book1));

        // Вызов метода контроллера
        ResponseEntity<Book> response = bookController.getBookById(1L);

        // Проверка ожидаемого результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book1, response.getBody());
        verify(bookService, times(1)).getBookById(1L); // Проверка вызова
    }

    @Test
    void getBookById_ThrowsException_WhenNotFound() {
        // Настройка поведения мок-сервиса для отсутствующей книги
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        // Проверка, что выбрасывается исключение
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookController.getBookById(1L);
        });

        // Проверка сообщения исключения
        assertEquals("Книга с id 1 не найдена", exception.getMessage());
        verify(bookService, times(1)).getBookById(1L); // Проверка вызова
    }

    @Test
    void createBook_ReturnsCreatedBook() {
        // Настройка поведения мок-сервиса для создания книги
        when(bookService.createBook(any(Book.class))).thenReturn(book1);

        // Вызов метода контроллера
        ResponseEntity<Book> response = bookController.createBook(book1);

        // Проверка ожидаемого результата
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book1, response.getBody());
        verify(bookService, times(1)).createBook(any(Book.class)); // Проверка вызова
    }

    @Test
    void updateBook_ReturnsUpdatedBook_WhenExists() {
        // Настройка поведения мок-сервиса для обновления книги
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book1);
        book1.setTitle("Animal Farm"); // Изменение названия книги

        // Вызов метода контроллера
        ResponseEntity<Book> response = bookController.updateBook(1L, book1);

        // Проверка ожидаемого результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book1, response.getBody());
        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class)); // Проверка вызова
    }

    @Test
    void updateBook_ThrowsException_WhenNotFound() {
        // Настройка поведения мок-сервиса для отсутствующей книги
        when(bookService.updateBook(eq(1L), any(Book.class))).thenThrow(new BookNotFoundException("Книга с id 1 не найдена"));

        // Проверка, что выбрасывается исключение
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookController.updateBook(1L, book1);
        });

        // Проверка сообщения исключения
        assertEquals("Книга с id 1 не найдена", exception.getMessage());
        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class)); // Проверка вызова
    }

    @Test
    void deleteBook_ReturnsNoContent() {
        // Настройка поведения мок-сервиса для удаления книги
        doNothing().when(bookService).deleteBook(1L);

        // Вызов метода контроллера
        ResponseEntity<Void> response = bookController.deleteBook(1L);

        // Проверка ожидаемого результата
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(1L); // Проверка вызова
    }
}