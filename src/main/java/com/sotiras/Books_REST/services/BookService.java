package com.sotiras.Books_REST.services;

import com.sotiras.Books_REST.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book create(Book book);

    Optional<Book> findById(String isbn);

    List<Book> findAll();

    Book save(Book book);

    boolean isBookExists(Book book);
    void delete(String isbn);
    Book update(String isbn, Book book);


}
