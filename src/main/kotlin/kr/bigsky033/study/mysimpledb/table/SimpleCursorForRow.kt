package kr.bigsky033.study.mysimpledb.table

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.Storage

class SimpleCursorForRow(
    private val storage: Storage<Row>,
    private var currentPosition: Int = 0
) : Cursor<Row> {

    private var current: Row? = null

    override fun get(): Row? {
        return current
    }

    override fun next(): Row? {
        return if (hasNext()) {
            currentPosition += 1
            current = storage.read(currentPosition)
            return current
        } else {
            null
        }
    }

    override fun hasNext(): Boolean {
        return storage.read(currentPosition + 1) != null
    }

    override fun add(data: Row) {
        // before write data, move cursor to according position
        currentPosition = data.id
        storage.write(data = data, offset = data.id)
    }

}