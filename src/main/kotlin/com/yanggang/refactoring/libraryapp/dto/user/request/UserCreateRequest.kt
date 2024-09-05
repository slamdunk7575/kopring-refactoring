package com.yanggang.refactoring.libraryapp.dto.user.request

data class UserCreateRequest(
    val name: String,
    val age: Int?,
)

/* 자바 코드에서 Lombok이 생상선 코드(예: getter) 를 코틀린에서 사용할 수 없어 직접 추가

    자바 코드와 코틀린 코드의 빌드 과정
    1. 코틀린 컴파일러가 코틀린 코드를 컴파일해 .class 파일 생성
    (이때 코틀린 코드가 참조하는 자바 코드도 함께 로딩되어 사용됨)

    2. 자바 컴파일러가 자바 코드를 컴파일해 .class 파일 생성
    (이때 이미 코틀린이 컴파일한 .class 파일의 경로를 classpath 에 추가해 컴파일함)
        2-1. Parse and Enter
        2-2. Annotation Processing
             - 이 단계가 Lombok 이 코드를 생성하는 단계
             - 코틀린 코드가 이미 컴파일된 이후이기 때문에 Lombok 이 생성한 코드(예: getter)를 코틀린에서 사용할 수 없음
        2-3. Analyze and Generate
*/
