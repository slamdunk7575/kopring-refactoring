package com.yanggang.refactoring.libraryapp.domain.user

import com.yanggang.refactoring.libraryapp.domain.book.Book
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanHistory
import jakarta.persistence.*

@Entity
@Table(name = "member")
class User(

    /* 코틀린과 JPA 를 함께 사용할때 생각해볼점
       : setter 대신 좋은 이름의 함수를 사용하는 것이 훨씬 클린코드다
       why?
       -> 네이밍을 줘서 의미(도메인 지식)를 명시적으로 나타낼 수 있음
       -> 여러 프로퍼티를 한번에 업데이트 시켜줄 수도 있음 (코드 응집성)

       Q. 하지만 현재 name 에 대한 setter 는 public 으로 열려있는 상태이기 때문에
       유저 이름을 업데이트 할때 setter 를 사용할수도 있다

       방법: backing property 사용하기
       예:
       class User(
          private var _name: String,
       ) {

          val name: String
          get() = this._name

       방법: custom setter 사용하기
       예:
       class User(
          name: String,
       ) {

          val name: String
            private set

       하지만, 두 방법 모두 클래스의 프로퍼티가 많아지면 번거롭다
       -> setter 를 열어두지만 사용하지 않는 방법 (Trade-Off 영역, 팀내 컨벤션 맞추기)

       (참고)
       Spring, JPA 와 같이 사용시 Proxy 사용을 위해 gradle 설정에 kotlin-allopen 을 사용하게 되는데
       이때 open 으로 두게 되면 var 변수에 대해서는 setter를 하위 클래스에서 override 할 수 있어야 하기 때문에
       private set을 만들 수 없다
       -> 우회책으로 접근 지시어 수준을 protected 로 변경하는 방법이 있다
       목적 자체가 setter 의 외부 사용을 막는 것이라 해당 클래스와 프록시로 인해 생성될
       하위 클래스에서만 사용할 수 있도록 protected 를 사용하는것
     */

    var name: String,

    val age: Int?,

    /* 코틀린과 JPA 를 함께 사용할때 생각해볼점
       : 꼭 primary constructor 안에 모든 프로퍼티를 넣어야 할까? 클래스 body 에 넣으면 안될까?
       -> 모든 프로퍼티를 생성자에 넣거나
       -> 현재 비지니스 로직상 User 생긴후 UserLoanHistory 가 따로 추가될 수 있기 때문에
       클래스 body 안에 구분해서 넣을 수 있음 (명확한 기준이 있을때)
     */

    // JAVA 와 차이점: cascade 옵션은 배열 타입(CascadeType[]) 이기 때문에 배열로 적어줌
    // Kotlin 에서는 Collection 을 가변 컬렉션 or 불변 컬렉션 으로 구분
    // 여기서는 데이터를 추가할 수 있기 때문에 가변 컬렉션으로 정의
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory(this, book.name))
    }

    fun returnBook(bookName: String) {
        this.userLoanHistories.first { history -> history.bookName == bookName }
            .doReturn()
    }
}
