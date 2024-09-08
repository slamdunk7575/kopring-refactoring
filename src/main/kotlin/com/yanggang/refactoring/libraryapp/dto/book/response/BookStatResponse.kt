package com.yanggang.refactoring.libraryapp.dto.book.response

import com.yanggang.refactoring.libraryapp.domain.book.BookType

data class BookStatResponse(
    val type: BookType,
    // 문제점1: 가변 필드가 있다 (즉, 이값이 어디서든 변경 될 수 있다)
    // var count: Int,
    val count: Long,
) {
    /*fun plusOneCount() {
        count++
    }*/

}
