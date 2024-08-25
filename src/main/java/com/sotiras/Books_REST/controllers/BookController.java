package com.sotiras.Books_REST.controllers;

import com.sotiras.Books_REST.domain.Book;
import com.sotiras.Books_REST.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createBook(@PathVariable final String isbn, @RequestBody final Book book) {
        book.setIsbn(isbn);
        final Book savedBook = bookService.create(book); // Save book using the service

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String isbn) {
        final Optional<Book> foundBook = bookService.findById(isbn);
        return foundBook
                .map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }
    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks() {
        return new ResponseEntity<List<Book>>(bookService.findAll(), HttpStatus.OK);
    }
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable final String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(path = "/books/{isbn}/update")
    public ResponseEntity<Book> updateBook(@PathVariable final String isbn, @RequestBody final Book book) {
        book.setIsbn(isbn);
        if (!bookService.isBookExists(book)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Book updatedBook = bookService.update(isbn, book);
        return ResponseEntity.ok(updatedBook);
    }


}
