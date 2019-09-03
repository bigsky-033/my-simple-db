package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.*
import kr.bigsky033.study.mysimpledb.table.SimpleTableForRow
import kr.bigsky033.study.mysimpledb.table.Table

class ApplicationContext {

    private lateinit var disk: Disk<Row>

    private lateinit var cache: Cache<Int, Row>

    private lateinit var table: Table<Row>

    private lateinit var database: Database

    fun initialize(
        filename: String = "my.db",
        maxCacheSize: Int = 100,
        maxDataSize: Int = 1000
    ): Database {
        disk = SimpleDiskForRow(filename = filename)
        disk.init()

        cache = SimpleCacheForRow()

        val storage = SimpleStorageForRow(cache = cache, disk = disk)

        table = SimpleTableForRow(storage = storage, maxDataSize = maxDataSize)

        database = MySimpleDatabase(table = table)
        return database
    }

    fun terminate() {
        disk.terminate()
    }

}