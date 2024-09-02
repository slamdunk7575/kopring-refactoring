package com.yanggang.refactoring.libraryapp.dto.book.request;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookLoanRequest {

    private String userName;
    private String bookName;

    @Builder
    public BookLoanRequest(String userName, String bookName) {
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
