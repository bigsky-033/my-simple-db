package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row

class SimpleStorageForRow(
    // TODO: implement cache and add cache related logic
    private val cache: Cache<Int, Row>,
    private val disk: Disk<Row>
) : Storage<Row> {

    override fun write(data: Row) {
        disk.write(data)
    }

    override fun read(id: Int): Row? {
        return disk.read(id)
    }

    override fun readSequentially(sequence: Int): Row? {
        return disk.readSequentially(sequence)
    }

    override fun currentSize(): Int {
        return disk.currentSize()
    }

}