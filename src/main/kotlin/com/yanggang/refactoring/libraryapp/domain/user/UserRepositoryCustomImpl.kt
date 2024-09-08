package com.yanggang.refactoring.libraryapp.domain.user

import com.querydsl.jpa.impl.JPAQueryFactory
import com.yanggang.refactoring.libraryapp.domain.user.QUser.user
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory

class UserRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : UserRepositoryCustom {

    override fun findAllWithHistories(): List<User> {
        return queryFactory.select(user).distinct()
            .from(user)
            .leftJoin(user.userLoanHistories)
            .fetchJoin()
            .fetch()
    }

    /*
    leftJoin(userLoanHistory)
    .on(userLoanHistory.user.id.eq(user.id)) 쓰지 않은 이유

    QueryDsl 의 on 기능이 두 테이블을 단지 연결만 해줄뿐, 실제 JPA 연관관계로 되어 있다는 것을 알려주지 못하기 때문에
    fetch join 쿼리가 나가지 않는다
    QueryDsl 온전한 fetch join 기능을 사용하고 싶다면, JPQL과 유사한 형태로 Querydsl을 작성해야함

    .leftJoin(user.userLoanHistories) 이렇게 하면
    user 와 userLoanHistory 가 연관관계에 있다는 것을 알려주게 된다
    */
}
