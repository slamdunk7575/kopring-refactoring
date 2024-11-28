package com.yanggang.refactoring.libraryapp.domain.user.loanhistory

import com.yanggang.refactoring.libraryapp.domain.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class UserLoanHistory(

    @ManyToOne
    val user: User,

    val bookName: String,

    /* 예:
    val isActive: Boolean, // 휴면 계정 유무
    val isDelete: Boolean, // 회원 탈퇴 유무

    Boolean 필드가 늘어나면 문제가 생긴다

    문제1. Boolean 이 많기 때문에 코드를 이해하기 어려워짐
    - 한 객체가 여러 상태를 표현할수록 이해하기 어렵다
    - 현재 상태의 경우의 수는 2^2=4 즉, 4가지 이다
    - 만약, Boolean 필드가 1개 더 늘어나면 2^3= 8가지 상태를 가지게 된다

    문제2. Boolean 필드 2개로 표현되는 4가지 상태가 모두 유효하지 않을 수 있다
    예: (isActive, isDelete)
    - (false, false) : 휴면 상태인 유저
    - (false, true) : 휴면이면서 탈퇴한 유저일수는 없다 (탈퇴를 하기 위해선 휴면해제후 로그인 해야함)
    - (true, false) : 활성화된 유저
    - (true, true) : 탈퇴한 유저

    두번째 경우는 DB에 존재 할 수 없는 경우이고, 이런 경우가 코드상에는 가능하다는 것이 유지보수를 어렵게 만든다

    해결책. Boolean 에도 Enum 활용
    1. 필드 1개로 여러가지 상태를 표현할 수 있기 때문에 코드가 이해하기 쉬워짐
    2. 정확하게 유의미한 상태만 나타낼 수 있기 때문에 유지보수가 쉬워진다
    */
    var status: UserLoanStatus = UserLoanStatus.LOANED,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    // fun isReturn(): Boolean = this.status == RETURNED
    val isReturn: Boolean // 프로퍼티 선언
        get() = this.status == UserLoanStatus.RETURNED // 커스텀 getter

    fun doReturn() {
        this.status = UserLoanStatus.RETURNED
    }

    companion object {
        fun fixture(
            user: User,
            bookName: String = "마이크로서비스 아키텍처 구축",
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null
        ): UserLoanHistory {
            return UserLoanHistory(
                user = user,
                bookName = bookName,
                status = status,
                id = id,
            )
        }
    }
}
