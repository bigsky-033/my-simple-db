package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleLinkedList
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleList


class StorageImpl : Storage {

    private val rows = SimpleLinkedList<Row>()

    companion object {
        private const val MAX_ROWS = 100
    }

    override fun addRow(row: Row) {
        if (this.rows.count() > MAX_ROWS) {
            throw IllegalStateException("Storage reaches limit. So cannot add $row to storage.")
        }
        this.rows.add(row)
    }

    override fun getRows(): SimpleList<Row> {
        return this.rows
    }

}