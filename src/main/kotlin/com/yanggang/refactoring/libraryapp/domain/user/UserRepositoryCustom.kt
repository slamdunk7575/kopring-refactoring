package com.yanggang.refactoring.libraryapp.domain.user

interface UserRepositoryCustom {

    fun findAllWithHistories(): List<User>
}
