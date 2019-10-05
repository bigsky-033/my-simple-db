package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File


class DiskTest {

    private val disk: Disk<Row> = BSTDiskForRow(tempDbFileName)

    @BeforeEach
    fun setup() {
        disk.init()
    }

    @AfterEach
    fun clear() {
        disk.clear()
    }

    companion object {
        private const val tempDbFileName = "hello.db"

        @AfterAll
        @JvmStatic
        fun cleanupResource() {
            val file = File(tempDbFileName)
            if (file.exists()) file.delete()
        }
    }

    @Test
    fun `read and write row`() {
        val id = 547
        val username = "sampleUser"
        val email = "my.email@email.com"
        val row = Row(id = id, username = username, email = email)
        disk.write(row)

        val read = disk.read(id)
        assertEquals(row, read)
    }

    @Test
    fun `read and write many rows`() {
        for (i in 5..100) {
            val row = Row(id = i, username = "user$i", email = "user$i@email.com")
            disk.write(row)
        }

        for (i in 5..100) {
            val row = disk.read(i)!!
            assertEquals(i, row.id)
            assertEquals("user$i", row.username)
            assertEquals("user$i@email.com", row.email)
        }
    }

    @Test
    fun `read invalid written offset`() {
        val row = disk.read(6777)
        assertNull(row)
    }

    @Test
    fun `identify current size`() {
        var counter = 0
        for (i in 5..100) {
            val row = Row(id = i, username = "user$i", email = "user$i@email.com")
            disk.write(row)
            counter++
        }

        assertEquals(counter, disk.currentSize())
    }

}