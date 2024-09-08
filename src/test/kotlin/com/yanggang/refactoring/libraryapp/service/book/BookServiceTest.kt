package com.yanggang.refactoring.libraryapp.service.book

import com.yanggang.refactoring.libraryapp.domain.book.Book
import com.yanggang.refactoring.libraryapp.domain.book.BookRepository
import com.yanggang.refactoring.libraryapp.domain.book.BookType
import com.yanggang.refactoring.libraryapp.domain.user.User
import com.yanggang.refactoring.libraryapp.domain.user.UserRepository
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.yanggang.refactoring.libraryapp.dto.book.request.BookLoanRequest
import com.yanggang.refactoring.libraryapp.dto.book.request.BookRequest
import com.yanggang.refactoring.libraryapp.dto.book.request.BookReturnRequest
import com.yanggang.refactoring.libraryapp.dto.book.response.BookStatResponse
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }


    @DisplayName("책을 등록 할 수 있다.")
    @Test
    fun save_book() {
        // given
        val bookRequest = BookRequest("코틀린 인 액션", BookType.COMPUTER)

        // when
        val savedBook = bookService.saveBook(bookRequest)

        // then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("코틀린 인 액션")
        assertThat(books[0].type).isEqualTo(BookType.COMPUTER)
    }

    @DisplayName("책을 대출할 수 있다.")
    @Test
    fun loan_book() {
        // given
        bookRepository.save(Book.fixture("코틀린 인 액션"))
        val savedUser = userRepository.save(
            User(
                "slamdunk7575",
                100
            )
        )
        val bookLoanRequest = BookLoanRequest("slamdunk7575", "코틀린 인 액션")

        // when
        bookService.loanBook(bookLoanRequest)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("코틀린 인 액션")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].status).isEqualTo(UserLoanStatus.LOANED)
    }

    @DisplayName("책이 이미 대출되어 있다면, 대출이 실패한다.")
    @Test
    fun loan_book_exception() {
        // given
        bookRepository.save(Book.fixture("코틀린 인 액션"))
        val savedUser = userRepository.save(
            User(
                "slamdunk7575",
                100
            )
        )
        userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser, "코틀린 인 액션"))
        val bookLoanRequest = BookLoanRequest("ABC", "코틀린 인 액션")

        // when & then
        assertThrows<IllegalArgumentException> {
            bookService.loanBook(bookLoanRequest)
        }.apply {
            assertThat(message).isEqualTo("이미 대출되어 있는 책입니다")
        }
    }

    @DisplayName("책 반납이 정상 동작한다.")
    @Test
    fun return_book() {
        // given
        val savedUser = userRepository.save(
            User(
                "slamdunk7575",
                100
            )
        )
        userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser, "코틀린 인 액션"))
        val bookReturnRequest = BookReturnRequest("slamdunk7575", "코틀린 인 액션")

        // when
        bookService.returnBook(bookReturnRequest)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].status).isEqualTo(UserLoanStatus.RETURNED)
    }

    @DisplayName("대출중인 책의 권수를 확인한다.")
    @Test
    fun count_loaned_book() {
        // given
        val savedUser = userRepository.save(User("slamdunk7575", 100))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser, "책1"),
            UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.RETURNED),
            UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.RETURNED),
        ))

        // when
        val loanedBookCount = bookService.countLoanedBook()

        // then
        assertThat(loanedBookCount).isEqualTo(1)
    }

    @DisplayName("카테고리별 책 권수를 정상 확인한다.")
    @Test
    fun get_Book_statistics() {
        // given
        bookRepository.saveAll(listOf(
            Book.fixture("책 1", BookType.COMPUTER),
            Book.fixture("책 2", BookType.COMPUTER),
            Book.fixture("책 3", BookType.SCIENCE),
        ))

        // when
        val bookStatistics = bookService.getBookStatistics()

        // then
        assertCount(bookStatistics, BookType.COMPUTER, 2)
        assertCount(bookStatistics, BookType.SCIENCE, 1)
    }

    private fun assertCount(results: List<BookStatResponse>, type: BookType, count: Int) {
        assertThat(results.first { dto -> dto.type == type }.count).isEqualTo(count)
    }
}
