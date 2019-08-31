package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.Cache
import kr.bigsky033.study.mysimpledb.storage.Disk
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleList

class SimpleTableForRow(
    private val cache: Cache<Int, Row>,
    private val disk: Disk<Row>,
    private val maxDataSize: Int
) : Table<Row> {

    override fun insert(data: Row) {
        disk.write(data)
        cache.add(data.id, data)
    }

    override fun select(id: Int): Row? {
        val rowFromCache = cache.get(id)
        return if (rowFromCache != null)
            rowFromCache
        else {
            val rowFromDisk = disk.read(id)
            if (rowFromDisk != null) {
                cache.add(id, rowFromDisk)
                cache.get(id)
            } else {
                null
            }
        }
    }

    override fun selectAll(): SimpleList<Row> {
        return disk.readAll()
    }

}