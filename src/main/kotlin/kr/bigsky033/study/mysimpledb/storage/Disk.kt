package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.storage.ds.SimpleList

interface Disk<T> {

    fun init()

    fun cleanup()

    fun write(data: T)

    fun write(data: SimpleList<T>)

    fun read(offset: Int): T?

    fun readAll(): SimpleList<T>

}