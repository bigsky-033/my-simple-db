package kr.bigsky033.study.mysimpledb.storage

interface Disk<T> {

    fun init()

    fun clear()

    fun terminate()

    fun write(data: T, offset: Int)

    fun read(offset: Int): T?

    fun currentSize(): Int

}