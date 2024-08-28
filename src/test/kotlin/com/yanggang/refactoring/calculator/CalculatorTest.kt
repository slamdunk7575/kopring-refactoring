package com.yanggang.refactoring.calculator

import com.yanggang.refactoring.libraryapp.calculator.Calculator
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CalculatorTest {

    @Test
    fun addTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.add(3)

        // then
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minusTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.minus(3)

        // then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun multiplyTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.multiply(3)

        // then
        assertThat(calculator.number).isEqualTo(15)
    }

    @Test
    fun divideTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.divide(3)

        // then
        assertThat(calculator.number).isEqualTo(1)
    }

    @Test
    fun divideExceptionTest() {
        // given
        val calculator = Calculator(5)

        // when & then
        val message = assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.message

        /*
        assertThrows 함수의 반환 타입을 보면, T 라고 되어 있음
        inline fun <reified T : Throwable> assertThrows(executable: () -> Unit): T {

        이 T 는 fun 과 assertThrows 사이에 있는 <reified T : Throwable> 의 T 를 의미
        따라서 이때 T는 우리가 < > 안에 넣어준 IllegalArgumentException 이 되고
        구체적으로는 { calculator.divide(0) } 라는 익명함수에서 throw 된 IllegalArgumentException 이 된다

        정리하면 assertThrows 는 함수 (executable)를 하나 받아서 그 함수를 실행시키고
        그 함수에서 주어진 타입 (IllegalArgumentException)과 동일한 예외가 발생할때 그 예외를 반환한다고 생각할 수 있음
        그리고 IllegalArgumentException 는 message 를 필드로 가지고 있어서
        위와 같이 IllegalArgumentException 에 담겨 있는 메시지를 확인 할 수 있는 것
        */

        assertThat(message).isEqualTo("0 으로 나눌 수 없습니다.")

        // scope 함수 활용한 방법
        /*assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.apply {
            assertThat(message).isEqualTo("0 으로 나눌 수 없습니다.")
        }*/
    }
}
