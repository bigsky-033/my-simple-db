package kr.bigsky033.study.mysimpledb

import org.junit.jupiter.api.Test

class DatabaseTest {

    private val applicationInitializer = TestApplicationInitializer()

    @Test
    fun `context loads`() {
        val database = applicationInitializer.initializeDatabase()
    }

}