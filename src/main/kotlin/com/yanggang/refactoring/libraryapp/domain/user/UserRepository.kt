package com.yanggang.refactoring.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {

    // 스프링이 코틀린을 지원하기 때문에 쿼리 결과가 null 인 경우 null 을 리턴
    fun findByName(name: String): User?


    /*
    JPQL 단점
    - 쿼리가 길어질수록 문자열이기 때문에 버그를 찾기 어렵다 (오타, 띄어쓰기 등)
    - JPQL 이 일반 SQL 문법과 조금 달라 복잡한 쿼리를 작성할 때마다 찾아봐야함

    Spring Data JPA 단점
    - 쿼리 메소드를 통해 쉽게 쿼리를 만들어 주지만 조건이 복잡한 동적쿼리를 작성할 때 함수가 계속 늘어나게 된다
    - 메소드에 필드이름이 들어가기 때문에 프로덕션 코드(도메인) 변경에 취약하다
    */

    /*@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userLoanHistories")
    fun findAllWithHistories(): List<User>*/
}
