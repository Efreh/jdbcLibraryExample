package com.efr.jdbcLibraryExample.repository;

import com.efr.jdbcLibraryExample.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

// Реализация интерфейса BookRepository для работы с базой данных
@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Определение RowMapper для маппинга результата запроса в объект Book
    private final RowMapper<Book> rowMapper = (rs, rowNum) ->
            new Book(rs.getLong("bookId"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("publicationYear"));

    @Override
    public List<Book> findAll() {
        // SQL-запрос для получения всех книг
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, rowMapper); // Выполнение запроса с маппингом
    }

    @Override
    public Optional<Book> findById(Long id) {
        // SQL-запрос для получения книги по идентификатору
        String sql = "SELECT * FROM book WHERE bookId = ?";
        List<Book> books = jdbcTemplate.query(sql, rowMapper, id);
        return books.stream().findFirst(); // Возврат первой найденной книги
    }

    @Override
    public Book save(Book book) {
        // SQL-запрос для добавления новой книги
        String sql = "INSERT INTO book (title, author, publicationYear) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"bookId"});
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, String.valueOf(book.getPublicationYear()));
            return ps; // Возвращаем подготовленный запрос
        }, keyHolder);

        // Получаем сгенерированный id из keyHolder
        Long generatedId = keyHolder.getKey().longValue();

        // Возвращаем объект Book с присвоенным id
        return new Book(generatedId, book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    @Override
    public Book update(Long id, Book book) {
        // SQL-запрос для обновления книги по идентификатору
        String sql = "UPDATE book SET title = ?, author = ?, publicationYear = ? WHERE bookId = ?";
        int rowsAffected = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), id);

        // Проверка, была ли обновлена хотя бы одна запись
        if (rowsAffected == 0) {
            throw new RuntimeException("Книга с id " + book.getBookId() + " не найдена");
        }
        return book; // Возврат обновленного объекта Book
    }

    @Override
    public void deleteById(Long id) {
        // SQL-запрос для удаления книги по идентификатору
        String sql = "DELETE FROM book WHERE bookId = ?";
        jdbcTemplate.update(sql, id);
    }
}