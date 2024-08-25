package com.sotiras.Books_REST.services.Impl;

import com.sotiras.Books_REST.domain.Book;
import com.sotiras.Books_REST.domain.BookEntity;
import com.sotiras.Books_REST.repositories.BookRepository;
import com.sotiras.Books_REST.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }

    @Override
    public Optional<Book> findById(final String isbn) {
        final Optional<BookEntity> foundBook = bookRepository.findById(isbn);
        return foundBook.map(this::bookEntityToBook);
    }

    @Override
    public List<Book> findAll() {
        final List<BookEntity> foundBooks = bookRepository.findAll();
        return foundBooks.stream().map(book->bookEntityToBook(book)).collect(Collectors.toList());
    }
    @Override
    public Book save(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }

    @Override
    public boolean isBookExists(Book book) {
        return bookRepository.existsById(book.getIsbn());
    }

    @Override
    public void delete(String isbn) {
        try {
            bookRepository.deleteById(isbn);

        } catch (final EmptyResultDataAccessException ex) {
            log.debug("Attempted to delete non-existing book", ex);
        }
    }

    @Override
    public Book update(String isbn, Book book) {
        if(!bookRepository.existsById(isbn)){
            throw new IllegalArgumentException(String.format("Book with %s does not exist",isbn));
        }
        book.setIsbn(isbn);
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }

    private BookEntity bookToBookEntity(final Book book) {
        return BookEntity.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }

    private Book bookEntityToBook(final BookEntity bookEntity) {
        return Book.builder()
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .build();
    }
}
