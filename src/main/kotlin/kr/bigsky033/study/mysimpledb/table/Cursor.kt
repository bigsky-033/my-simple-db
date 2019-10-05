package kr.bigsky033.study.mysimpledb.table

interface Cursor<T> {

    fun get(id: Int): T?

    fun next(): T?

    fun hasNext(): Boolean

    fun add(data: T)

}