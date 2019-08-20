package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleList

class SimpleListBasedStorage(
    private val rows: SimpleList<Row>,
    private val maxRows: Int
) : Storage {

    override fun addRow(row: Row) {
        if (this.rows.count() > maxRows) {
            throw IllegalStateException("Storage reaches limit(current max: $maxRows). So cannot add $row to storage.")
        }
        this.rows.add(row)
    }

    override fun getRows(): SimpleList<Row> {
        return this.rows
    }

}