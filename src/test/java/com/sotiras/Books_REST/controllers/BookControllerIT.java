package com.sotiras.Books_REST.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sotiras.Books_REST.TestData;
import com.sotiras.Books_REST.domain.Book;
import com.sotiras.Books_REST.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testBookCreated() throws Exception {
        final Book book = TestData.TestBook();
        final String json = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Expect 201 Created
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()));
    }
    @Test
    public void testThatRetrieveBookReturns404WhenBookNotFound() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/books/123123123"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveBookReturnsHttp200AndBookWhenExists() throws Exception {
        final Book book = TestData.TestBook();
        bookService.save(book);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/books/" + book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }
    @Test
    public void testThatListBooksReturnsHttp200EmptyListWhenNoBooksExist() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testThatListBooksReturnsHttp200AndBooksWhenBooksExist() throws Exception {
        final Book book = TestData.TestBook();
        bookService.save(book);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author").value(book.getAuthor()));
    }
    @Test
    public void testThatHttp204IsReturnedWhenBookDoesntExist() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/books/21321213"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatHttp204IsReturnedWhenExistingBookIsDeleted() throws Exception {
        final Book book = TestData.TestBook();
        bookService.save(book);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/books/" + book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
