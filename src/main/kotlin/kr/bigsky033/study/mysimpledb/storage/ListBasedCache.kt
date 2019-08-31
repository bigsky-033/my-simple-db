package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleList

class ListBasedCache(
    private val rows: SimpleList<Row>,
    private val maxCacheSize: Int
) : Cache<Int, Row> {

    override fun add(key: Int, value: Row): Int {
        //TODO: need to implement evicition logic using maxCacheSize
        rows.add(value)
        return value.id
    }

    override fun get(id: Int): Row? {
        return rows.get(id)
    }

    override fun getAsList(): SimpleList<Row> {
        return rows;
    }

}