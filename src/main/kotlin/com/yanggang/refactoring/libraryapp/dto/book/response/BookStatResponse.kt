package com.yanggang.refactoring.libraryapp.dto.book.response

import com.yanggang.refactoring.libraryapp.domain.book.BookType

data class BookStatResponse(
    val type: BookType,
    var count: Int,
) {
    fun plusOneCount() {
        count++
    }

}
