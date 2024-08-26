package com.yanggang.refactoring.libraryapp.controller.book;

import com.yanggang.refactoring.libraryapp.dto.book.request.BookLoanRequest;
import com.yanggang.refactoring.libraryapp.dto.book.request.BookRequest;
import com.yanggang.refactoring.libraryapp.dto.book.request.BookReturnRequest;
import com.yanggang.refactoring.libraryapp.service.book.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book")
    public void saveBook(@RequestBody BookRequest request) {
        bookService.saveBook(request);
    }

    @PostMapping("/book/loan")
    public void loanBook(@RequestBody BookLoanRequest request) {
        bookService.loanBook(request);
    }

    @PutMapping("/book/return")
    public void returnBook(@RequestBody BookReturnRequest request) {
        bookService.returnBook(request);
    }

}
