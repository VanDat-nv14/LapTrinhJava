package com.example.bookapi.service;

import com.example.bookapi.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    
    Optional<Book> getBookById(Long id);
    
    Book addBook(Book book);
    
    Optional<Book> updateBook(Long id, Book book);
    
    boolean deleteBook(Long id);
}
