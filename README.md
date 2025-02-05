# Приложение для управления библиотекой

Это простое приложение для управления списком книг в библиотеке, разработанное с использованием Spring Data JDBC.
Приложение предоставляет RESTful API для выполнения операций CRUD (создание, чтение, обновление, удаление) с книгами.

## Описание проекта

Проект реализует функционал для управления библиотекой, включая следующие функции:

- Просмотр списка всех книг
- Поиск книги по идентификатору
- Добавление новой книги
- Обновление информации о книге
- Удаление книги

Сущности, используемые в приложении:

- **Book**: содержит информацию о книге, включая идентификатор, название, автора и год публикации.

## Технологии

- **Java 17**: Язык программирования, используемый для разработки приложения.
- **Spring Boot**: Фреймворк для упрощения разработки приложений на Java.
- **Spring Data JDBC**: Библиотека для работы с реляционными базами данных.
- **H2 Database**: Встраиваемая база данных для разработки и тестирования.
- **JUnit**: Инструмент для написания тестов.

## Установка

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/ваш-username/ваш-репозиторий.git
   ```
2. Перейдите в директорию проекта:

```bash
 cd ваш-репозиторий
```

3. Запустите приложение:

```bash
 ./mvnw spring-boot:run
```

## Использование

После запуска приложения вы можете взаимодействовать с API через Postman или аналогичный инструмент. Все запросы должны
быть направлены на адрес http://localhost:8080/api/books.

# Примеры запросов

- Получить все книги:
   ```http

    GET /api/books

   ```
- Получить книгу по ID:
   ```http
    GET /api/books/{id}
     ```
- Создать новую книгу:

   ```http
    POST /api/books
    Content-Type: application/json
    
    {
    "title": "Название книги",
    "author": "Автор книги",
    "publicationYear": 2024
    }
   ```
- Обновить книгу:

   ```http
    PUT /api/books/{id}
    Content-Type: application/json
    
    {
    "title": "Новое название книги",
    "author": "Новый автор",
    "publicationYear": 2024
    }
   ```
- Удалить книгу:

   ```http
    DELETE /api/books/{id}
   ```

## Тестирование

Для запуска тестов используйте следующую команду:

   ```bash
  ./mvnw test
   ```

- Тесты написаны с использованием JUnit и охватывают различные сценарии для контроллера.