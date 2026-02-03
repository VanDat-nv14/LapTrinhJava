package phattrienungdung2ee.bai2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import phattrienungdung2ee.bai2.model.Book;
import phattrienungdung2ee.bai2.service.BookService;

import java.util.List;
import java.util.Map;

/**
 * Một controller cho cả REST API (JSON) và giao diện web (Thymeleaf).
 * - /api/books/*  → trả JSON (Postman, API)
 * - /books/*      → trả trang HTML (trình duyệt)
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    // ========== REST API (JSON) - /api/books ==========

    @GetMapping(value = {"/api/books", "/api/books/"})
    @ResponseBody
    public List<Book> getAllBooksApi() {
        return bookService.getAllBooks();
    }

    @GetMapping(value = {"/api/books/{id}", "/api/books/{id}/"})
    @ResponseBody
    public ResponseEntity<Book> getBookByIdApi(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping(value = {"/api/books", "/api/books/"})
    @ResponseBody
    public String addBookApi(@RequestBody Book book) {
        bookService.addBook(book);
        return "Book added successfully!";
    }

    @PutMapping(value = {"/api/books/{id}", "/api/books/{id}/"})
    @ResponseBody
    public String updateBookApi(@PathVariable int id, @RequestBody Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        return "Book updated successfully!";
    }

    @PatchMapping(value = {"/api/books/{id}", "/api/books/{id}/"})
    @ResponseBody
    public ResponseEntity<String> patchBookApi(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        if (bookService.patchBook(id, updates)) {
            return ResponseEntity.ok("Book patched successfully!");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = {"/api/books/{id}", "/api/books/{id}/"})
    @ResponseBody
    public String deleteBookApi(@PathVariable int id) {
        bookService.deleteBook(id);
        return "Book deleted successfully!";
    }

    // ========== Web (Thymeleaf) - /books ==========

    @GetMapping(value = {"/books", "/books/"})
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/books/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String editBookForm(@PathVariable int id, Model model) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/books/edit")
    public String updateBook(@ModelAttribute Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
