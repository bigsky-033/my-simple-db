package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row

class SimpleStorageForRow(
    // TODO: implement cache and add cache related logic
    private val cache: Cache<Int, Row>,
    private val disk: Disk<Row>
) : Storage<Row> {

    override fun write(data: Row, offset: Int) {
        disk.write(data, offset)
    }

    override fun read(offset: Int): Row? {
        return disk.read(offset)
    }

    override fun currentSize(): Int {
        return disk.currentSize()
    }

}