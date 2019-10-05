package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row

interface Storage<T> {

    fun write(data: T)

    fun read(id: Int): Row?

    fun readSequentially(sequence: Int): Row?

    fun currentSize(): Int

}