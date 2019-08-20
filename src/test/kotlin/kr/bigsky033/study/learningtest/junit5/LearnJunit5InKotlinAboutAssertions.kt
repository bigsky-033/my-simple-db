package kr.bigsky033.study.learningtest.junit5

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class LearnJunit5InKotlinAboutAssertions {

    // references from https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions-kotlin

    @Test
    fun `hello junit5`() {
        val s = "hello"
        assertEquals("hello", s)
    }

    @Test
    fun `expected exception throws`() {
        val str = "wow"
        val exception = assertThrows<IllegalArgumentException>("should throw an exception") {
            exceptionThrows(str)
        }
        assertEquals("$str is illegal", exception.message)
    }

    private fun exceptionThrows(arg: String) {
        throw IllegalArgumentException("$arg is illegal")
    }

    @Test
    fun `grouped assertions`() {
        val person = Person("hello", "world")
        assertAll("Person properties",
            { assertEquals("hello", person.firstName) },
            { assertEquals("world", person.lastName) }
        )
    }

    data class Person(
        val firstName: String,
        val lastName: String
    )

}