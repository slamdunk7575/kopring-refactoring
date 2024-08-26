package com.yanggang.refactoring.libraryapp.dto.book.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookReturnRequest {

    private String userName;
    private String bookName;

    @Builder
    public BookReturnRequest(String userName, String bookName) {
        this.userName = userName;
        this.bookName = bookName;
    }
}
