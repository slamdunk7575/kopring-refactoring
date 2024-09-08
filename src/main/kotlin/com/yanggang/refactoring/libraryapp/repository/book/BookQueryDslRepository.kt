package com.yanggang.refactoring.libraryapp.repository.book

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.yanggang.refactoring.libraryapp.domain.book.QBook.book
import com.yanggang.refactoring.libraryapp.dto.book.response.BookStatResponse
import org.springframework.stereotype.Component

/*
방법2. QueryDsl
장점: 클래스만 만들면 되기 때문에 간결하다
단점: 필요에 따라 두 Repository를 모두 불러와야 한다

Q. 어떤 방식이 더 좋은가?
-> 개인적으로 두번째 방식을 선호
이유는 멀티 모듈을 사용하는 경우에 모듈 별로 Repository 를 만들어 쓰는 경우가 많기 때문에

예: Core 모듈이 있고 A 모듈, B 모듈 이 있으면,
Core 모듈에서는 스프링 데이터 JPA Repository 를 다른 모듈들에서 모두 사용할 수 있게 하고
모듈에서 각각 QueryDsl Repository 를 따로 만들어서 사용함
*/

@Component
class BookQueryDslRepository(
    private val queryFactory: JPAQueryFactory,
) {

    fun getStats(): List<BookStatResponse> {
        return queryFactory.select(
            Projections.constructor(
                BookStatResponse::class.java,
                book.type,
                book.id.count()
            ))
            .from(book)
            .groupBy(book.type)
            .fetch()
    }

}
