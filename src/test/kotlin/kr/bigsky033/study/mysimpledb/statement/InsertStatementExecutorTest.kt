package kr.bigsky033.study.mysimpledb.statement

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.SimpleListBasedStorage
import kr.bigsky033.study.mysimpledb.storage.Storage
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleLinkedList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class InsertStatementExecutorTest {

    private lateinit var storage: Storage

    @BeforeEach
    fun setup() {
        val simpleLinkedList = SimpleLinkedList<Row>()
        storage = SimpleListBasedStorage(simpleLinkedList, 10)
    }

    @Test
    fun `execute insert statement`() {
        val id = 1
        val username = "user1"
        val email = "hello@email.com"
        val statement = InsertStatement("$id $username $email")
        val insertStatementExecutor = InsertStatementExecutor()
        insertStatementExecutor.execute(statement, storage)

        val rows = storage.getRows()
        assertEquals(1, rows.count())
        assertNotNull(rows.get(0))

        val row = rows.get(0)!!.value
        assertAll("row properties",
            { assertEquals(id, row.id) },
            { assertEquals(username, row.username) },
            { assertEquals(email, row.email) }
        )
    }

    @Test
    fun `expected illegal arguments exception throws when input statement is not InsertStatement`() {
        val statement = SelectStatement()
        val insertStatementExecutor = InsertStatementExecutor()
        assertThrows<IllegalArgumentException> {
            insertStatementExecutor.execute(statement, storage)
        }
    }

    @Test
    fun `expected illegal arguments exception throws when token's size is not proper`() {
        val statement = InsertStatement("I'm not valid insert statement's content")
        val insertStatementExecutor = InsertStatementExecutor()
        assertThrows<IllegalArgumentException> {
            insertStatementExecutor.execute(statement, storage)
        }
    }

    @Test
    fun `expected illegal arguments exception throws when id value is not number`() {
        assertThrows<IllegalArgumentException> {
            executeInsertStatement(id = "hello")
        }
    }

    @Test
    fun `expected illegal arguments exception throws when id value is negative number`() {
        assertThrows<IllegalArgumentException> {
            executeInsertStatement(id = "-1")
        }
    }

    @Test
    fun `expected illegal arguments exception throws when username exceeds max length`() {
        val username = StringBuilder()
        for (i in 1..32) {
            username.append("dummy")
        }
        assertThrows<IllegalArgumentException> {
            executeInsertStatement(username = username.toString())
        }
    }

    @Test
    fun `expected illegal arguments exception throws when email exceeds max length`() {
        val email = StringBuilder()
        for (i in 1..256) {
            email.append("dummy")
        }
        assertThrows<IllegalArgumentException> {
            executeInsertStatement(username = email.toString())
        }
    }

    private fun executeInsertStatement(
        id: String = "1",
        username: String = "user1",
        email: String = "user1@email.com"
    ) {
        val statement = InsertStatement("$id $username $email")
        val insertStatementExecutor = InsertStatementExecutor()
        insertStatementExecutor.execute(statement, storage)
    }

}