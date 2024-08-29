package com.yanggang.refactoring.libraryapp.domain.user

import com.yanggang.refactoring.libraryapp.domain.book.Book
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanHistory
import jakarta.persistence.*

@Entity
@Table(name = "member")
class User(

    var name: String,

    val age: Int?,

    // JAVA 와 차이점: cascade 옵션은 배열 타입(CascadeType[]) 이기 때문에 배열로 적어줌
    // Kotlin 에서는 Collection 을 가변 컬렉션 or 불변 컬렉션 으로 구분
    // 여기서는 데이터를 추가할 수 있기 때문에 가변 컬렉션으로 정의
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory(this, book.name, false))
    }

    fun returnBook(bookName: String) {
        this.userLoanHistories.first { history -> history.bookName == bookName }
            .doReturn()
    }
}
