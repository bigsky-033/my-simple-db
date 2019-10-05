package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class BSTDiskForRowTest {

    @Test
    fun `test node serialize & deserialize node`() {
        val row = Row(id = 23, username = "hello", email = "wow@email.com")
        val node = Node(valid = true, id = row.id, left = 2356, right = 9411123, n = 999913, data = row)
        val node2 = Node.fromBytes(node.toBytes())

        assertEquals(node, node2)
    }

}