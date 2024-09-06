package com.yanggang.refactoring.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {

    // 스프링이 코틀린을 지원하기 때문에 쿼리 결과가 null 인 경우 null 을 리턴
    fun findByName(name: String): User?

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userLoanHistories")
    fun findAllWithHistories(): List<User>
}
