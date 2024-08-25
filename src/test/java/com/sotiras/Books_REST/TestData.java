package com.sotiras.Books_REST;

import com.sotiras.Books_REST.domain.Book;
import com.sotiras.Books_REST.domain.BookEntity;

public final class TestData {
    private TestData() {

    }
    public static Book TestBook(){
        return Book.builder()
                .isbn("8245676787")
                .author("Virgin Wolf")
                .title("the waves")
                .build();
    }
    public static BookEntity TestBookEntity(){
        return BookEntity.builder()
                .isbn("8245676787")
                .author("Virgin Wolf")
                .title("the waves")
                .build();
    }
}
