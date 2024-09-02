package com.yanggang.refactoring.libraryapp.domain.book

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository : JpaRepository<Book, Long> {

    // 코틀린은 Book? or Book 으로 nullable 유무를 표현할 수 있지만 현재 자바 Service 와 호환성을 위해 Optional 로 표현
    fun findByName(bookName: String): Optional<Book>
}
