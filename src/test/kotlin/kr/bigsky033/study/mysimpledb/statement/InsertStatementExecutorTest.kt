package kr.bigsky033.study.mysimpledb.statement

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.SimpleTableForRow
import kr.bigsky033.study.mysimpledb.Table
import kr.bigsky033.study.mysimpledb.storage.ListBasedCache
import kr.bigsky033.study.mysimpledb.storage.SimpleDiskForRow
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleLinkedList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.io.File

class InsertStatementExecutorTest {

    private lateinit var table: Table<Row>

    private val filename = "test.db"

    @BeforeEach
    fun setup() {
        val file = File(filename)
        if (file.exists()) file.delete()

        val disk = SimpleDiskForRow(filename = filename)
        disk.init()
        val simpleLinkedList = SimpleLinkedList<Row>()
        val cache = ListBasedCache(rows = simpleLinkedList, maxCacheSize = 10)
        table = SimpleTableForRow(cache = cache, disk = disk, maxDataSize = 100)
    }

    @Test
    fun `execute insert statement`() {
        val id = 1
        val username = "user1"
        val email = "hello@email.com"
        val statement = InsertStatement("$id $username $email")
        val insertStatementExecutor = InsertStatementExecutor()
        insertStatementExecutor.execute(statement, table)

        val rows = table.selectAll()
        assertEquals(1, rows.count())
        assertNotNull(rows.get(0))

        val row = rows.get(0)!!
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
            insertStatementExecutor.execute(statement, table)
        }
    }

    @Test
    fun `expected illegal arguments exception throws when token's size is not proper`() {
        val statement = InsertStatement("I'm not valid insert statement's content")
        val insertStatementExecutor = InsertStatementExecutor()
        assertThrows<IllegalArgumentException> {
            insertStatementExecutor.execute(statement, table)
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
        insertStatementExecutor.execute(statement, table)
    }

}