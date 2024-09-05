package com.yanggang.refactoring.libraryapp.domain.book

import jakarta.persistence.*

@Entity
class Book(
    val name: String,

    @Enumerated(EnumType.STRING)
    val type: BookType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
    }


    /* Test Fixture 사용
    Book 엔티티에 필드가 추가 되더라도 Test 코드에까지 영향범위가 전파되지 않음
    즉, 테스트 코드가 수십, 수백개가 있다면 모두 수정하지 않아도됨

    (참고) 이런 패턴을 Object Mother 패턴이라고 함

    DTO 에도 이런 Test Fixture 를 만들어 사용할 수 있음
    Entity 는 테스트에서 사용되는 범위가 넒기 때문에 Test Fixture 를 주로 만들지만
    DTO 는 해당 API 만 테스트 하는 경우가 많기 때문에 팀과 논의하여 결정하는게 좋음
    */

    // companion object 가장 아래에 위치하는 것이 컨벤션
    companion object {
        fun fixture(
            name: String = "책 이름",
            type: BookType = BookType.COMPUTER,
            id: Long? = null,
        ): Book {
            return Book(
                name = name,
                type = type,
                id = id,
            )
        }
    }
}
