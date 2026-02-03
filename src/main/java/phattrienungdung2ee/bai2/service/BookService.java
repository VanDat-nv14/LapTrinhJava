package phattrienungdung2ee.bai2.service;

import org.springframework.stereotype.Service;
import phattrienungdung2ee.bai2.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private int nextId = 6; // Sau 5 sách mẫu

    public BookService() {
        // Dữ liệu mẫu sẵn khi khởi động (giống demo)
        books.add(new Book(1, "Spring boot", "Huy Cương"));
        books.add(new Book(2, "Spring Boot V2", "Anh"));
        books.add(new Book(3, "Đắc nhân tâm", "Dale Carnegie"));
        books.add(new Book(4, "Lập trình Java", "Nguyễn Văn A"));
        books.add(new Book(5, "Spring Boot in Action", "Craig Walls"));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addBook(Book book) {
        if (book.getId() == 0) {
            book.setId(nextId++);
        }
        books.add(book);
    }

    public void updateBook(int id, Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }

    /** Gọi từ form sửa (gửi cả book có id). */
    public void updateBook(Book book) {
        updateBook(book.getId(), book);
    }

    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }

    /**
     * Cập nhật một phần (PATCH): chỉ cập nhật các trường có trong map.
     */
    public boolean patchBook(int id, Map<String, Object> updates) {
        Book book = getBookById(id);
        if (book == null) return false;
        if (updates.containsKey("title") && updates.get("title") != null) {
            book.setTitle(updates.get("title").toString());
        }
        if (updates.containsKey("author") && updates.get("author") != null) {
            book.setAuthor(updates.get("author").toString());
        }
        return true;
    }
}
