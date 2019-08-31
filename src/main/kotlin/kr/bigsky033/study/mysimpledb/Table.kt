package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleList

interface Table<T> {

    fun insert(data: T)

    fun select(id: Int): Row?

    fun selectAll(): SimpleList<T>

}