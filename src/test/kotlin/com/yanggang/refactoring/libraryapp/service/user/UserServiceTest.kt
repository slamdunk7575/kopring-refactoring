package com.yanggang.refactoring.libraryapp.service.user

import com.yanggang.refactoring.libraryapp.domain.user.UserRepository
import com.yanggang.refactoring.libraryapp.dto.user.request.UserCreateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @Test
    fun save_user_test() {
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

}
