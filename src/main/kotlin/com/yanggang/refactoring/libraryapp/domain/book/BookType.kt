package com.yanggang.refactoring.libraryapp.domain.book

/*
Type 을 string 으로 관리할때 단점
1. 검증이 되고 있지 않고 검증 코드를 추가 작성하기 번거롭다 -> DTO 에서 잘못된 입력 검증 가능해짐
2. 코드만 봤을때 어떤 값이 DB 에 있는지 알 수 없다 -> Enum 을 보고 어떤 값들이 들어갈 수 있는지 확인
3. Type 과 관련한 새로운 로직을 추가할때 번거롭다 -> Enum 클래스 안에 추가 로직을 구현할 수 있음

(추가 이점)
- Enum은 다형성을 활용해 코드에 분기가 없어짐
- 실행되지 않을 else 문도 제거되어 함수가 깔끔해짐
- 예를들어, score를 추가한다고 했을때 Enum 클래스에 위임해 추가 문자열 타이핑도 사라짐 (새로운 타입 추가시 score 를 빼먹을 수 없게됨)
*/


enum class BookType {
    COMPUTER,
    ECONOMY,
    SOCIETY,
    LANGUAGE,
    SCIENCE,
}
