//package kr.bigsky033.study.mysimpledb.table.statement
//
//import kr.bigsky033.study.mysimpledb.entity.Row
//import kr.bigsky033.study.mysimpledb.table.SimpleTableForRow
//import kr.bigsky033.study.mysimpledb.table.Table
//import kr.bigsky033.study.mysimpledb.storage.ListBasedCache
//import kr.bigsky033.study.mysimpledb.storage.SimpleDiskForRow
//import kr.bigsky033.study.mysimpledb.storage.ds.SimpleLinkedList
//import org.junit.jupiter.api.*
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.assertTrue
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.io.PrintStream
//
//class SelectStatementExecutorTest {
//
//    private val outContent = ByteArrayOutputStream()
//
//    private val originalOut = System.out
//
//    private lateinit var table: Table<Row>
//
//    private val filename = "test.db"
//
//    @BeforeEach
//    fun setup() {
//        // set for output stream
//        System.setOut(PrintStream(outContent))
//
//        val file = File(filename)
//        if (file.exists()) file.delete()
//
//        val disk = SimpleDiskForRow(filename = filename)
//        disk.init()
//        val simpleLinkedList = SimpleLinkedList<Row>()
//        val cache = ListBasedCache(rows = simpleLinkedList, maxCacheSize = 10)
//        table = SimpleTableForRow(cache = cache, disk = disk, maxDataSize = 100)
//    }
//
//    @AfterEach
//    fun cleanup() {
//        // set for output stream
//        System.setOut(originalOut)
//    }
//
//    @Test
//    fun `expect Empty result when there is not data`() {
//        val statement = SelectStatement()
//        val selectStatementExecutor = SelectStatementExecutor()
//
//        selectStatementExecutor.execute(statement, table)
//
//        val output = outContent.toString(Charsets.UTF_8).trim()
//        assertEquals("Empty", output)
//    }
//
//    @Test
//    fun `expect data when there is data`() {
//        val id = 456
//        val username = "bisky033"
//        val email = "hello@email.com"
//        val row = Row(id = id, username = username, email = email)
//        table.insert(row)
//
//        val statement = SelectStatement()
//        val selectStatementExecutor = SelectStatementExecutor()
//
//        selectStatementExecutor.execute(statement, table)
//
//        val output = outContent.toString(Charsets.UTF_8).trim()
//        assertAll("output should contains",
//            { assertTrue(output.contains(id.toString())) },
//            { assertTrue(output.contains(username)) },
//            { assertTrue(output.contains(email)) }
//        )
//    }
//
//    @Test
//    fun `expected illegal arguments exception throws when input statement is not SelectStatement`() {
//        val statement = InsertStatement("hello")
//        val selectStatementExecutor = SelectStatementExecutor()
//        assertThrows<IllegalArgumentException> {
//            selectStatementExecutor.execute(statement, table)
//        }
//    }
//
//}