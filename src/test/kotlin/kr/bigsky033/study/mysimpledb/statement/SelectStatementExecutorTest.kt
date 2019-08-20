package kr.bigsky033.study.mysimpledb.statement

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.SimpleListBasedStorage
import kr.bigsky033.study.mysimpledb.storage.Storage
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleLinkedList
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SelectStatementExecutorTest {

    private val outContent = ByteArrayOutputStream()

    private val originalOut = System.out

    private lateinit var storage: Storage

    @BeforeEach
    fun setup() {
        System.setOut(PrintStream(outContent))

        val simpleLinkedList = SimpleLinkedList<Row>()
        storage = SimpleListBasedStorage(rows = simpleLinkedList, maxRows = 10)
    }

    @AfterEach
    fun cleanup() {
        System.setOut(originalOut)
    }

    @Test
    fun `expect Empty result when there is not data`() {
        val statement = SelectStatement()
        val selectStatementExecutor = SelectStatementExecutor()

        selectStatementExecutor.execute(statement, storage)

        val output = outContent.toString(Charsets.UTF_8).trim()
        assertEquals("Empty", output)
    }

    @Test
    fun `expect data when there is data`() {
        val id = 456
        val username = "bisky033"
        val email = "hello@email.com"
        val row = Row(id = id, username = username, email = email)
        storage.addRow(row)

        val statement = SelectStatement()
        val selectStatementExecutor = SelectStatementExecutor()

        selectStatementExecutor.execute(statement, storage)

        val output = outContent.toString(Charsets.UTF_8).trim()
        assertAll("output should contains",
            { assertTrue(output.contains(id.toString())) },
            { assertTrue(output.contains(username)) },
            { assertTrue(output.contains(email)) }
        )
    }

    @Test
    fun `expected illegal arguments exception throws when input statement is not SelectStatement`() {
        val statement = InsertStatement("hello")
        val selectStatementExecutor = SelectStatementExecutor()
        assertThrows<IllegalArgumentException> {
            selectStatementExecutor.execute(statement, storage)
        }
    }

}