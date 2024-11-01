package com.efr.jdbcLibraryExample.service;

import com.efr.jdbcLibraryExample.exception.BookNotFoundException;
import com.efr.jdbcLibraryExample.model.Book;
import com.efr.jdbcLibraryExample.repository.BookRepository;
import com.efr.jdbcLibraryExample.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Реализация сервиса для работы с книгами
@Service
public class BookServiceImpl implements BookService {

    // Репозиторий для доступа к данным книг
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        // Получение всех книг из репозитория
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        // Получение книги по идентификатору из репозитория
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public Book createBook(Book book) {
        // Сохранение новой книги в репозитории
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(Long id, Book bookDetails) {
        // Обновление существующей книги
        return bookRepository.findById(id).map(book -> {
            // Установка новых значений для книги
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setPublicationYear(bookDetails.getPublicationYear());
            // Обновление книги в репозитории и возврат обновленного объекта
            return bookRepository.update(book.getBookId(), book);
        }).orElseThrow(() -> new BookNotFoundException("Книга с id " + id + " не найдена"));
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        // Удаление книги по идентификатору из репозитория
        bookRepository.deleteById(id);
    }
}