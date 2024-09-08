package com.yanggang.refactoring.libraryapp.repository.user.loanhistory

import com.querydsl.jpa.impl.JPAQueryFactory
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanStatus
import org.springframework.stereotype.Component

@Component
class UserLoanHistoryQueryDslRepository(
    private val queryFactory: JPAQueryFactory,
) {
    /*
    스프링 데이터 JPA 의 쿼리 메소드를 사용하면 동적쿼리를 작성하기 위해 메소드가 늘어나는 단점이 있음
    QueryDsl 을 사용하면 간결하게 동적 쿼리를 만들 수 있다
     */
    fun find(bookName: String, status: UserLoanStatus? = null): UserLoanHistory? {
        return queryFactory.select(userLoanHistory)
            .from(userLoanHistory)
            .where(
                userLoanHistory.bookName.eq(bookName),
                status?.let { userLoanHistory.status.eq(status) } // status 가 null 이 아닌 경우 조건 확인
            )
            .limit(1)
            .fetchOne()
    }

    fun count(status: UserLoanStatus): Long {
        return queryFactory.select(userLoanHistory.id.count())
            .from(userLoanHistory)
            .where(
                userLoanHistory.status.eq(status)
            )
            .fetchOne() ?: 0L
    }
}
