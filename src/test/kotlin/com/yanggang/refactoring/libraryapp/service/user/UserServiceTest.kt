package com.yanggang.refactoring.libraryapp.service.user

import com.yanggang.refactoring.libraryapp.domain.user.User
import com.yanggang.refactoring.libraryapp.domain.user.UserRepository
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.yanggang.refactoring.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.yanggang.refactoring.libraryapp.dto.user.request.UserCreateRequest
import com.yanggang.refactoring.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @DisplayName("유저를 저장할 수 있다.")
    @Test
    fun save_user() {
        // given
        val request = UserCreateRequest("slamdunk7575", null)

        // when
        userService.saveUser(request)

        // then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("slamdunk7575")

        // getAge(...) must not be null
        // 실패 이유: 플랫폼 타입 (코틀린이 null 관련 정보를 알 수 없는 타입)
        // 즉, getAge() 한 값이 null 인지 아닌지 알 수 없기 때문에 자바코드에 명시적으로 @Nullable 추가
        assertThat(results[0].age).isNull()
    }

    @DisplayName("유저 정보를 조회할 수 있다.")
    @Test
    fun get_users() {
        // given
        userRepository.saveAll(
            listOf(
                User("A", 20),
                User("B", null)
            )
        )

        // when
        val result = userService.getUsers()

        // then
        assertThat(result).hasSize(2)
        assertThat(result).extracting("name").containsExactlyInAnyOrder("A", "B")
        assertThat(result).extracting("age").containsExactlyInAnyOrder(20, null)
    }

    @DisplayName("유저 정보를 업데이트 할 수 있다.")
    @Test
    fun update_user_name() {
        // given
        val savedUser = userRepository.save(
            User(
                "A",
                null
            )
        )

        // 코틀린 User의 id값은 null 이 가능한 Long 타입이고
        // 자바 UserUpdateRequest의 id는 null 이 불가능한 long 타입임
        // 해결: 한번 저장이 되고난 이후는 절대 id 가 null 이 될 수 없기 때문에 null 아님 단언 사용!
        val request = UserUpdateRequest(savedUser.id!!, "B")

        // when
        userService.updateUserName(request)

        // then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @DisplayName("유저를 삭제 할 수 있다.")
    @Test
    fun delete_user() {
        // given
        userRepository.save(User("A", null))

        // when
        userService.deleteUser("A")

        // then
        assertThat(userRepository.findAll()).isEmpty()
    }

    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다")
    @Test
    fun get_user_empty_loan_histories() {
        // given
        userRepository.save(User("yanggang", 100))

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("yanggang")
        assertThat(results[0].books).isEmpty()
    }

    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다")
    @Test
    fun get_user_loan_histories() {
        // given
        val savedUser = userRepository.save(User("yanggang", 100))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser, "클린코드1", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser, "클린코드2", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser, "클린코드3", UserLoanStatus.RETURNED),
        ))

        // when
        val results = userService.getUserLoanHistories()

        /*
        유저가 여러명인 경우 아래처럼 결과를 가져와서 검증할 수도 있음
        val userResult = results.first { it.name == "yanggang" }
        */

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("yanggang")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("클린코드1", "클린코드2", "클린코드3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)
    }
}
