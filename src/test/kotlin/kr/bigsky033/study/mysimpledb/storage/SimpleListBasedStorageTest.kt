package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleLinkedList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

class SimpleListBasedStorageTest {

    private lateinit var storage: Storage

    @BeforeEach
    fun setup() {
        val simpleLinkedList = SimpleLinkedList<Row>()
        storage = SimpleListBasedStorage(rows = simpleLinkedList, maxRows = 10)
    }

    @Test
    fun `expect illegal state exception throws when attempts to add row after reached to max`() {
        val simpleLinkedList = SimpleLinkedList<Row>()
        val storage = SimpleListBasedStorage(rows = simpleLinkedList, maxRows = 5)

        for (i in 1..5) {
            storage.addRow(Row(id = Random.nextInt(), username = "hello", email = "hello@email.com"))
        }

        assertThrows<IllegalStateException> {
            storage.addRow(Row(id = Random.nextInt(), username = "hello", email = "hello@email.com"))
        }
    }

}