package com.yanggang.refactoring.libraryapp.dto.book.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookRequest {

    private String name;

    public BookRequest(String name) {
        this.name = name;
    }
}
