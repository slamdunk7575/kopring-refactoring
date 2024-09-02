package com.yanggang.refactoring.libraryapp.service.user

import com.yanggang.refactoring.libraryapp.domain.user.User
import com.yanggang.refactoring.libraryapp.domain.user.UserRepository
import com.yanggang.refactoring.libraryapp.dto.user.request.UserCreateRequest
import com.yanggang.refactoring.libraryapp.dto.user.request.UserUpdateRequest
import com.yanggang.refactoring.libraryapp.dto.user.response.UserResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    /* 코틀린은 기본적으로 class 선언시 상속이 불가능 하도록 막혀있다
    함수 역시 기본적으로 override 가 불가능하다
    -> 위 둘을 가능하게 하고 싶다면, 각각 open 키워드를 붙여줘야함
    -> 클래스와 함수에 매번 open 을 붙이는건 번거롭게 때문에 플러그인(kotlin("plugin.spring")) 을 사용
    */
    @Transactional
    fun saveUser(request: UserCreateRequest) {
        // id, userLoanHistories 는 Default Parameter 사용
        val newUser = User(request.name, request.age)
        userRepository.save(newUser)
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<UserResponse> {
        return userRepository.findAll()
            // .map { user -> UserResponse(user) }
            // .map { UserResponse(it) }
            .map(::UserResponse)
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest) {
        val user = userRepository.findById(request.id).orElseThrow(::IllegalArgumentException)
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val user = userRepository.findByName(name).orElseThrow(::IllegalArgumentException)
        userRepository.delete(user)
    }
}
