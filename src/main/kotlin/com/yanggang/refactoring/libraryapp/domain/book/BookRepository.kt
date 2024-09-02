package com.yanggang.refactoring.libraryapp.domain.book

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository : JpaRepository<Book, Long> {

    fun findByName(bookName: String): Optional<Book>
}
