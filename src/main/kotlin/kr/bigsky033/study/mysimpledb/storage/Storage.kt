package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleList


interface Storage {

    fun addRow(row: Row)

    fun getRows(): SimpleList<Row>

}