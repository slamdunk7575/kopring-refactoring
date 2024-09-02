package com.yanggang.refactoring.libraryapp.service.book

import com.yanggang.refactoring.libraryapp.domain.book.Book
import com.yanggang.refactoring.libraryapp.domain.book.BookRepository
import com.yanggang.refactoring.libraryapp.domain.user.UserRepository
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.yanggang.refactoring.libraryapp.dto.book.request.BookLoanRequest
import com.yanggang.refactoring.libraryapp.dto.book.request.BookRequest
import com.yanggang.refactoring.libraryapp.dto.book.request.BookReturnRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,

) {

    @Transactional
    fun saveBook(request: BookRequest) {
        val newBook = Book(request.name)
        bookRepository.save(newBook)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest) {
        val book  = bookRepository.findByName(request.bookName)
            .orElseThrow(::IllegalArgumentException)

        if (userLoanHistoryRepository.findByBookNameAndIsReturn(request.bookName, false) != null) {
            throw IllegalArgumentException("이미 대출되어 있는 책입니다")
        }

        val user = userRepository.findByName(request.userName)
            .orElseThrow(::IllegalArgumentException)
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest) {
        val user = userRepository.findByName(request.userName)
            .orElseThrow(:: IllegalArgumentException)
        user.returnBook(request.bookName)
    }
}
