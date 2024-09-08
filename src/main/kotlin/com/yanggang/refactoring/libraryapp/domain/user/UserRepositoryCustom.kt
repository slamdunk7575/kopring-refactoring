package com.yanggang.refactoring.libraryapp.domain.user

/*
방법1. QueryDsl
장점: Service 단에서 UserRepository 하나만 사용하면 된다
단점: 인터페이스와 클래스를 항상 같이 만들어 주어야 하는 것이 번거로울 수 있다
*/

interface UserRepositoryCustom {

    fun findAllWithHistories(): List<User>
}
