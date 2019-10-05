package kr.bigsky033.study.mysimpledb.storage

import kr.bigsky033.study.mysimpledb.entity.Row
import java.io.File
import java.io.RandomAccessFile

abstract class AbstractDiskForRow(private val filename: String) : Disk<Row> {

    private lateinit var file: File

    protected lateinit var randomAccessFile: RandomAccessFile

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

}