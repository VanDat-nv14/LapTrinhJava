package com.example.bookapi.service;

import com.example.bookapi.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookServiceImpl implements BookService {
    
    private final Map<Long, Book> books = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public Book addBook(Book book) {
        Long id = idGenerator.getAndIncrement();
        book.setId(id);
        books.put(id, book);
        return book;
    }

    @Override
    public Optional<Book> updateBook(Long id, Book book) {
        if (books.containsKey(id)) {
            book.setId(id);
            books.put(id, book);
            return Optional.of(book);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteBook(Long id) {
        return books.remove(id) != null;
    }
}
