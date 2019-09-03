package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row

interface Storage<T> {

    fun write(data: T, offset: Int)

    fun read(offset: Int): Row?

    fun currentSize(): Int

}