package com.sotiras.Books_REST.repositories;

import com.sotiras.Books_REST.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,String> {
}
