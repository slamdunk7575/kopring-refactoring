package com.yanggang.refactoring.libraryapp.controller.user

import com.yanggang.refactoring.libraryapp.dto.user.request.UserCreateRequest
import com.yanggang.refactoring.libraryapp.dto.user.request.UserUpdateRequest
import com.yanggang.refactoring.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.yanggang.refactoring.libraryapp.dto.user.response.UserResponse
import com.yanggang.refactoring.libraryapp.service.user.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/user")
    fun saveUser(@RequestBody request: UserCreateRequest) {
        userService.saveUser(request)
    }

    @GetMapping("/user")
    fun getUsers(): List<UserResponse> {
        return userService.getUsers()
    }

    /* 코틀린은 아래처럼도 작성 가능
    @GetMapping("/user")
    fun getUsers(): List<UserResponse> = userService.getUsers()
    */

    @PutMapping("/user")
    fun updateUserName(@RequestBody request: UserUpdateRequest) {
        userService.updateUserName(request)
    }

    @DeleteMapping("/user")
    fun deleteUser(@RequestParam name: String) {
        /*
        코틀린 @RequestParam 의 required 속성은 기본값이 true 인데 아래처럼 String? 으로 선언하면
        null 이 들어올 수 있기 때문에 required 속성이 false 로 바뀜
        fun deleteUser(@RequestParam name: String?) {
         */
        userService.deleteUser(name)
    }

    /*
    Controller 를 구분하는 3가지 방법
    1. 화면에서 사용되는 API 끼리 모아 둔다
    장점: 화면에서 어떤 API 사용되는지 한눈에 알기 쉽다
    단점:
    - 한 API 가 여러 화면에서 사용되면 위치가 애매하다
    - 서버 코드가 화면에 종속된다

    2. 동일한 도메인끼리 API를 모아 둔다 (현재 사용)
    장점:
    - 화면 위치와 무관하게 서버 코드는 변경되지 않아도 된다
    - 비슷한 API 끼리 모이게 코드의 위치를 예측할 수 있다
    단점: 이 API 어디에 사용되는지 서버 코드만 보고 알기는 어렵다

    3. (간혹) 1 API 1 Controller 를 사용한다
    장점: 화면 위치와 무관하게 서버 코드는 변경되지 않아도 된다
    단점: 이 API 어디에 사용되는지 서버 코드만 보고 알기는 어렵다
    */

    @GetMapping("/user/loan")
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userService.getUserLoanHistories()
    }
}
