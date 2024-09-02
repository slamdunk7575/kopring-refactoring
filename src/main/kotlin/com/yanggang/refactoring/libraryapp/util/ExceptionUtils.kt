package com.yanggang.refactoring.libraryapp.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun fail(): Nothing {
    throw IllegalArgumentException()
}


/*
(추가) 직접 확장함수를 만들어서 줄이기
CrudRepository<T, ID>.findByIdOrNull(id) ?: fail() 감싸서 직접 확장함수를 만듦

<엔티티 타입, ID> CrudRepository<T, ID>.함수명(id: ID): T {
    return CrudRepository<T, ID>.findByIdOrNull(id) ?: fail()
}
-> 예외를 던지기 때문에 T? 가 아니라 T 리턴
 */
fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id) ?: fail()
}
