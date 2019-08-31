package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleLinkedList
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleList
import kr.bigsky033.study.mysimpledb.storage.serde.toCsv
import kr.bigsky033.study.mysimpledb.storage.serde.toRow
import java.io.*

class SimpleDiskForRow(private val filename: String) : Disk<Row> {

    private lateinit var file: File

    private lateinit var writer: BufferedWriter

    override fun init() {
        if (!filename.endsWith(".db")) {
            throw IllegalArgumentException("filename should be ends with .db but current input is $filename")
        }
        file = File(filename)
        if (!file.exists()) file.createNewFile()
        if (!file.isFile) throw IllegalStateException("filename is not valid. It is not file. input: $filename")
        writer = BufferedWriter(FileWriter(file, Charsets.UTF_8, true))
    }

    override fun cleanup() {
        writer.flush()
        writer.close()
    }

    override fun write(data: Row) {
        writer.write(data.toCsv())
        writer.newLine()
        writer.flush()
    }

    override fun write(data: SimpleList<Row>) {
        data.forEach {
            writer.write(it.toCsv())
            writer.newLine()
        }
        writer.flush()
    }

    override fun read(offset: Int): Row? {
        TODO("not implemented")
    }

    override fun readAll(): SimpleList<Row> {
        val list = SimpleLinkedList<Row>()
        file.bufferedReader(Charsets.UTF_8).use {
            var line = it.readLine()
            while (line != null) {
                val row = line.toRow(",")
                list.add(row)
                line = it.readLine()
            }
        }
        return list
    }

}