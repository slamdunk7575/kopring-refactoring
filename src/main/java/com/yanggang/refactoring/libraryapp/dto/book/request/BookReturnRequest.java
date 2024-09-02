package com.yanggang.refactoring.libraryapp.dto.book.request;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookReturnRequest {

    private String userName;
    private String bookName;

    @Builder
    public BookReturnRequest(String userName, String bookName) {
        this.userName = userName;
        this.bookName = bookName;
    }

    public String getUserName() {
        return userName;
    }

    public String getBookName() {
        return bookName;
    }
}
