package kr.bigsky033.study.mysimpledb.storage.ds

interface SimpleList<T> {

    fun first(): Node<T>?

    fun last(): Node<T>?

    fun isEmpty(): Boolean

    fun count(): Int

    fun get(index: Int): Node<T>?

    fun add(value: T)

    fun clear()

}

data class Node<T>(
    var value: T,
    var next: Node<T>? = null
)


