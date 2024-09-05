package com.yanggang.refactoring.libraryapp.controller.user

import com.yanggang.refactoring.libraryapp.dto.user.request.UserCreateRequest
import com.yanggang.refactoring.libraryapp.dto.user.request.UserUpdateRequest
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

    /* 코틀린은 아래처럼 작성 가능
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
}
