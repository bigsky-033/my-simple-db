package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleList

interface Cache<K, V> {

    fun add(key: K, value: V): K

    fun get(id: K): Row?

    fun getAsList(): SimpleList<V>

}