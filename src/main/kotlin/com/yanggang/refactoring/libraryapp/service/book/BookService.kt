package com.yanggang.refactoring.libraryapp.service.book

import com.yanggang.refactoring.libraryapp.domain.book.Book
import com.yanggang.refactoring.libraryapp.domain.book.BookRepository
import com.yanggang.refactoring.libraryapp.domain.user.UserRepository
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.yanggang.refactoring.libraryapp.dto.book.request.BookLoanRequest
import com.yanggang.refactoring.libraryapp.dto.book.request.BookRequest
import com.yanggang.refactoring.libraryapp.dto.book.request.BookReturnRequest
import com.yanggang.refactoring.libraryapp.dto.book.response.BookStatResponse
import com.yanggang.refactoring.libraryapp.util.fail
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
        val newBook = Book(request.name, request.type)
        bookRepository.save(newBook)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest) {
        val book  = bookRepository.findByName(request.bookName) ?: fail()

        if (userLoanHistoryRepository.findByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED) != null) {
            throw IllegalArgumentException("이미 대출되어 있는 책입니다")
        }

        val user = userRepository.findByName(request.userName) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest) {
        val user = userRepository.findByName(request.userName) ?: fail()
        user.returnBook(request.bookName)
    }

    @Transactional(readOnly = true)
    fun countLoanedBook(): Int {
        // DB 및 네트워크, 애플리케이션에 부하가 덜 들도록 쿼리로 조회
        return userLoanHistoryRepository.countAllByStatus(UserLoanStatus.LOANED).toInt()
    }

    @Transactional(readOnly = false)
    fun getBookStatistics(): List<BookStatResponse> {
        /*
        네트워크, 애플리케이션에 부하를 덜 주고 index 를 이용해 튜닝할 여지도 있기 때문에 쿼리로 조회

        (정리)
        데이터양, 트래픽, 요구사항 등에 따라 또 다른 방법을 사용해야 할 수도 있다
        예를들면,
        대용량 통계 처리 배치를 이용한 구조
        이벤트 발행과 메시징 큐를 이용한 구조
        */
        return bookRepository.getStats()

        /* 문제점: 전체 데이터를 모두 메모리로 가져와서 groupBy 수행
        return bookRepository.findAll()
            .groupBy { book -> book.type }
            .map { (type, books) -> BookStatResponse(type, books.size) }
        */

        /*
        // 문제점1: 가변 리스트가 있다 (즉, 이 리스트가 변경될 수 있다 -> 코드를 처음본 사람이 수정했을때 실수를 만들 수 있다)
        val results = mutableListOf<BookStatResponse>()
        val books = bookRepository.findAll()
        for (book in books) {
            // 문제점2: 콜체인이 긴 코드는 코드를 이해하고 유지보수 하는데 어렵다
            results.firstOrNull { dto -> book.type == dto.type }?.plusOneCount()
                ?: results.add(BookStatResponse(book.type, 1))
        }
        return results
        */
    }
}
