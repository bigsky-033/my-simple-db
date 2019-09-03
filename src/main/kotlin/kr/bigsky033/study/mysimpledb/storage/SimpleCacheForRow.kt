package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row

class SimpleCacheForRow : Cache<Int, Row> {

    override fun add(key: Int, value: Row): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(id: Int): Row? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}