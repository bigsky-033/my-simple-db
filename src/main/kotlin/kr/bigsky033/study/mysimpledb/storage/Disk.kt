package kr.bigsky033.study.mysimpledb.storage

interface Disk<T> {

    fun init()

    fun clear()

    fun terminate()

    fun write(data: T)

    fun read(id: Int): T?

    fun readSequentially(sequence: Int): T?

    fun currentSize(): Int

}