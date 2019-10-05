package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.serde.toCsv
import kr.bigsky033.study.mysimpledb.storage.serde.toRow
import java.nio.ByteBuffer

class BSTDiskForRow(filename: String) : AbstractDiskForRow(filename) {

    private val rootNodeOffset = 0.toLong()

    override fun terminate() {
        TODO("")
    }

    override fun write(data: Row) {
        val node = Node(valid = true, id = data.id, data = data)

        val root = readNode(rootNodeOffset)
        write(root, rootNodeOffset, node)
    }

    private fun write(current: Node, offsetToWrite: Long, nodeToWrite: Node): Long {
        if (!current.valid) {
            nodeToWrite.n = 1
            writeNode(nodeToWrite, offsetToWrite)
            return offsetToWrite
        }
        val cmp = nodeToWrite.id.compareTo(current.id)
        when {
            cmp < 0 -> {
                val left = readNode(current.left)
                if (left.valid) {
                    current.left = write(left, current.left, nodeToWrite)
                } else {
                    current.left = write(left, offsetToWrite + NODE_SIZE, nodeToWrite)
                }
            }
            cmp > 0 -> {
                val right = readNode(current.right)
                if (right.valid) {
                    current.right = write(right, current.right, nodeToWrite)
                } else {
                    current.right = write(right, offsetToWrite + NODE_SIZE, nodeToWrite)
                }
            }
            else -> throw IllegalArgumentException("already exists")
        }
        current.n = size(current.left) + size(current.right) + 1
        writeNode(current, offsetToWrite)
        return offsetToWrite
    }

    private fun writeNode(node: Node, offset: Long) {
        randomAccessFile.seek(offset)
        randomAccessFile.write(node.toBytes())
    }

    override fun read(id: Int): Row? {
        val root = readNode(rootNodeOffset)
        val found = read(root, id)
        return found?.data
    }

    private fun read(current: Node, id: Int): Node? {
        if (!current.valid) {
            return null
        }
        val cmp = id.compareTo(current.id)
        return when {
            cmp < 0 -> read(readNode(current.left), id)
            cmp > 0 -> read(readNode(current.right), id)
            else -> current
        }
    }

    override fun readSequentially(sequence: Int): Row? {
        return readNode((sequence * NODE_SIZE).toLong()).data
    }

    private fun readNode(offset: Long): Node {
        if (offset < 0) return Node(valid = false)
        val dataBuffer = ByteArray(NODE_SIZE)
        randomAccessFile.seek(offset)
        randomAccessFile.read(dataBuffer, 0, NODE_SIZE)
        return Node.fromBytes(dataBuffer)
    }

    override fun currentSize(): Int {
        return readNode(rootNodeOffset).n
    }

    private fun size(offset: Long): Int {
        val node = readNode(offset)
        return node.n
    }

}

data class Node(
    var valid: Boolean = false,
    val id: Int = -1,
    var left: Long = -1,
    var right: Long = -1,
    var n: Int = 0,
    val data: Row? = null
) {

    companion object {
        fun fromBytes(bytes: ByteArray): Node {
            return when (bytes[0]) {
                VALID -> {
                    var currentOffset = VALID_BYTE_SIZE

                    val id = ByteBuffer.wrap(
                        bytes.sliceArray(currentOffset until currentOffset + ID_BYTE_SIZE)
                    ).int
                    currentOffset += ID_BYTE_SIZE

                    val left = ByteBuffer.wrap(
                        bytes.sliceArray(currentOffset until currentOffset + CHILD_NODE_START_OFFSET_SIZE)
                    ).long
                    currentOffset += CHILD_NODE_START_OFFSET_SIZE

                    val right = ByteBuffer.wrap(
                        bytes.sliceArray(currentOffset until currentOffset + CHILD_NODE_START_OFFSET_SIZE)
                    ).long
                    currentOffset += CHILD_NODE_START_OFFSET_SIZE

                    val n = ByteBuffer.wrap(
                        bytes.sliceArray(currentOffset until currentOffset + CHILD_NODE_START_OFFSET_SIZE)
                    ).int
                    currentOffset += NODE_NUMBER_SIZE

                    val rowSize = ByteBuffer.wrap(
                        bytes.sliceArray(currentOffset until currentOffset + DATA_CONTENT_LENGTH_BYTE_SIZE)
                    ).int
                    currentOffset += DATA_CONTENT_LENGTH_BYTE_SIZE

                    val row = String(
                        bytes.sliceArray(currentOffset until currentOffset + rowSize),
                        Charsets.UTF_8
                    ).toRow(",")

                    return Node(valid = true, id = id, left = left, right = right, n = n, data = row)
                }
                INVALID -> Node()
                else -> throw IllegalArgumentException("input bytes is not valid.")
            }
        }
    }

    fun toBytes(): ByteArray {
        if (data == null) throw IllegalStateException("data should not be null")
        var currentOffset = 0
        val byteArray = ByteArray(NODE_SIZE)

        // 1. add valid byte
        byteArray[0] = if (valid) 1.toByte() else 0.toByte()
        currentOffset += VALID_BYTE_SIZE

        // 2. add id
        val idBytes = ByteBuffer.allocate(ID_BYTE_SIZE).putInt(id).array()
        System.arraycopy(idBytes, 0, byteArray, currentOffset, idBytes.size)
        currentOffset += idBytes.size

        // 3. add left child offset
        val leftChildOffsetBytes = ByteBuffer.allocate(CHILD_NODE_START_OFFSET_SIZE).putLong(left).array()
        System.arraycopy(leftChildOffsetBytes, 0, byteArray, currentOffset, leftChildOffsetBytes.size)
        currentOffset += leftChildOffsetBytes.size

        // 4. add right child offset
        val rightChildOffsetBytes = ByteBuffer.allocate(CHILD_NODE_START_OFFSET_SIZE).putLong(right).array()
        System.arraycopy(rightChildOffsetBytes, 0, byteArray, currentOffset, rightChildOffsetBytes.size)
        currentOffset += rightChildOffsetBytes.size

        // 5. add node number
        val nodeNumberBytes = ByteBuffer.allocate(NODE_NUMBER_SIZE).putInt(n).array()
        System.arraycopy(nodeNumberBytes, 0, byteArray, currentOffset, nodeNumberBytes.size)
        currentOffset += nodeNumberBytes.size

        // 5. add content
        val contentBytes = data.toCsv().toByteArray(Charsets.UTF_8)
        val bufferForContentSize = ByteBuffer.allocate(DATA_CONTENT_LENGTH_BYTE_SIZE)
        val sizeArray = bufferForContentSize.putInt(contentBytes.size).array()
        System.arraycopy(sizeArray, 0, byteArray, currentOffset, sizeArray.size)
        currentOffset += sizeArray.size

        System.arraycopy(contentBytes, 0, byteArray, currentOffset, contentBytes.size)

        return byteArray
    }

}
