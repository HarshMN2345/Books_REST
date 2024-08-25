package com.sotiras.Books_REST.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    private String isbn;
    private String title;
    private String author;
}
