package com.sotiras.Books_REST.services.impl;

import com.sotiras.Books_REST.domain.Book;
import com.sotiras.Books_REST.domain.BookEntity;
import com.sotiras.Books_REST.repositories.BookRepository;
import com.sotiras.Books_REST.services.Impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sotiras.Books_REST.TestData.TestBook;
import static com.sotiras.Books_REST.TestData.TestBookEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
    public class BookServiceImplTest {

        @Mock
        private BookRepository bookRepository;
        @InjectMocks
        private BookServiceImpl underTest;
        @Test
        public void testThatBookIsSaved(){
            final Book book = TestBook();
            final BookEntity bookEntity=TestBookEntity();
            when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);
            final Book result = underTest.create(book);
            assertEquals(book,result);

        }
        @Test
        public void testThatFindByIdReturnsEmptyWhenNoBook(){
            final String isbn="123123123";
            when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());
            final Optional<Book> result = underTest.findById(isbn);
            assertEquals(Optional.empty(),result);
        }
        @Test
        public void testThatFindByIdReturnsBook(){
            final Book book = TestBook();
            final BookEntity bookEntity=TestBookEntity();
            when(bookRepository.findById(eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));
            final Optional<Book> result = underTest.findById(book.getIsbn());
            assertEquals(Optional.of(book),result);
        }
    @Test
    public void testListBooksReturnsEmptyListWhenNoBooksExist() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<BookEntity>());
        final List<Book> result = underTest.findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testListBooksReturnsBooksWhenExist() {
        final BookEntity bookEntity = TestBookEntity();
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.findAll();
        assertEquals(1, result.size());
    }
    @Test
    public void testIsBookExistsReturnsFalseWhenBookDoesntExist() {
        when(bookRepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.isBookExists(TestBook());
        assertEquals(false, result);
    }

    @Test
    public void testDeleteBookDeletesBook() {
        final String isbn = "13123213";
        underTest.delete(isbn);
        verify(bookRepository, times(1)).deleteById(eq(isbn));
    }
}

