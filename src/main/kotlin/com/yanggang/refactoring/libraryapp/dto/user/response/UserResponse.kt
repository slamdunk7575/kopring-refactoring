package com.yanggang.refactoring.libraryapp.dto.user.response

import com.yanggang.refactoring.libraryapp.domain.user.User

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?,
) {

    // 정적 팩토리 메소드 사용
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                // 위의 id 는 null 이 불가능한 타입이기 때문에 null 아님 단언 사용
                id = user.id!!,
                name = user.name,
                age = user.age,
            )
        }
    }

    // 부생성자 (this 를 통해 주생성자 호출)
    /*constructor(user: User): this(
        id = user.id!!,
        name = user.name,
        age = user.age,
    )*/

    /*init {
        id = user.id!!
        name = user.name
        age = user.age
    }*/

}
