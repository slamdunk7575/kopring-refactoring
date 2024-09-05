package com.yanggang.refactoring.libraryapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

// 코틀린은 탑라인에 여러 클래스와 함수를 만들수 있음
// 함수를 만드는 경우 static 함수로 생성됨
@SpringBootApplication
class LibraryAppApplication

fun main(args: Array<String>) {

    /* runApplication 은 확장함수
    inline fun <reified T : Any> runApplication(vararg args: String): ConfigurableApplicationContext =
        SpringApplication.run(T::class.java, *args)
    */

    // 코틀린에서 가변인자 배열을 메소드 파라미터로 전달시 spread 연산자를 쓸 수 있음
    runApplication<LibraryAppApplication>(*args)
}
