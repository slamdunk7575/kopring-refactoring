package com.yanggang.refactoring.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long> {

    // fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory?

    // fun findAllByStatus(status: UserLoanStatus): List<UserLoanHistory>

    // fun countAllByStatus(status: UserLoanStatus): Long
}
