package com.yanggang.refactoring.libraryapp.dto.book.request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookRequest {

    private String name;

    public BookRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
