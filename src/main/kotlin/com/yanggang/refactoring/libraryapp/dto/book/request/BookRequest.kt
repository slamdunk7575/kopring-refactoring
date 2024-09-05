package com.yanggang.refactoring.libraryapp.dto.book.request

import com.yanggang.refactoring.libraryapp.domain.book.BookType

data class BookRequest(
    val name: String,
    val type: BookType,
)
