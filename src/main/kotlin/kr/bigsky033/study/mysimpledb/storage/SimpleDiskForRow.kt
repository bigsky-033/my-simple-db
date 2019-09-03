package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.serde.toCsv
import kr.bigsky033.study.mysimpledb.storage.serde.toRow
import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteBuffer

class SimpleDiskForRow(private val filename: String) : Disk<Row> {

    private val dataRowSize = VALID_BYTE_SIZE + DATA_CONTENT_LENGTH_BYTE_SIZE + DATA_MAX_LENGTH_BYTE_SIZE

    private lateinit var file: File

    private lateinit var randomAccessFile: RandomAccessFile

    override fun init() {
        if (!filename.endsWith(".db")) {
            throw IllegalArgumentException("filename should be ends with .db but current input is $filename")
        }
        file = File(filename)
        if (!file.exists()) file.createNewFile()
        if (!file.isFile) throw IllegalStateException("filename is not valid. It is not file. input: $filename")
        randomAccessFile = RandomAccessFile(file, "rw")
    }

    override fun clear() {
        if (file.exists()) file.delete()
        init()
    }

    override fun terminate() {
    }

    override fun write(data: Row, offset: Int) {
        val dataBuffer = ByteArray(dataRowSize)

        val startOffset = (offset - 1) * dataRowSize
        randomAccessFile.seek(startOffset.toLong())

        dataBuffer[0] = VALID

        val content = data.toCsv().toByteArray(Charsets.UTF_8)
        val bufferForContentSize = ByteBuffer.allocate(DATA_CONTENT_LENGTH_BYTE_SIZE)
        val sizeArray = bufferForContentSize.putInt(content.size).array()
        System.arraycopy(sizeArray, 0, dataBuffer, 1, sizeArray.size)
        System.arraycopy(
            content, 0, dataBuffer,
            VALID_BYTE_SIZE + DATA_CONTENT_LENGTH_BYTE_SIZE, content.size
        )
        randomAccessFile.write(dataBuffer)
    }


    override fun read(offset: Int): Row? {
        val dataBuffer = ByteArray(dataRowSize)

        val startOffset = (offset - 1) * dataRowSize
        randomAccessFile.seek(startOffset.toLong())
        randomAccessFile.read(dataBuffer, 0, dataRowSize)

        return when (dataBuffer[0]) {
            VALID -> {
                val contentSize = ByteBuffer.wrap(
                    dataBuffer.sliceArray(1..DATA_CONTENT_LENGTH_BYTE_SIZE)
                ).int

                val startPositionOfContent = VALID_BYTE_SIZE + DATA_CONTENT_LENGTH_BYTE_SIZE
                val content = String(
                    dataBuffer.sliceArray(startPositionOfContent until startPositionOfContent + contentSize),
                    Charsets.UTF_8
                )
                content.toRow(",")
            }
            INVALID -> null
            else -> throw IllegalArgumentException("input offset is not valid. input: $offset")
        }
    }

    override fun currentSize(): Int {
        var currentSize = 0

        var offset = 1
        randomAccessFile.seek(0)

        while (randomAccessFile.filePointer + dataRowSize < randomAccessFile.length()) {
            val startOffset = (offset - 1) * dataRowSize
            randomAccessFile.seek(startOffset.toLong())
            val validByte = randomAccessFile.readByte()
            if (validByte == VALID) currentSize++
            offset++
        }

        return currentSize
    }
}