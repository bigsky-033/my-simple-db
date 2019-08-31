package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.statement.InsertStatementExecutor
import kr.bigsky033.study.mysimpledb.statement.SelectStatementExecutor
import kr.bigsky033.study.mysimpledb.storage.Cache
import kr.bigsky033.study.mysimpledb.storage.Disk
import kr.bigsky033.study.mysimpledb.storage.ListBasedCache
import kr.bigsky033.study.mysimpledb.storage.SimpleDiskForRow
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleLinkedList

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

        val simpleLinkedList = SimpleLinkedList<Row>()
        cache = ListBasedCache(rows = simpleLinkedList, maxCacheSize = maxCacheSize)

        table = SimpleTableForRow(cache = cache, disk = disk, maxDataSize = maxDataSize)

        val insertStatementExecutor = InsertStatementExecutor()
        val selectStatementExecutor = SelectStatementExecutor()

        database = MySimpleDatabase(
            table = table,
            insertStatementExecutor = insertStatementExecutor,
            selectStatementExecutor = selectStatementExecutor
        )
        return database
    }

    fun terminate() {
        disk.cleanup()
    }

}