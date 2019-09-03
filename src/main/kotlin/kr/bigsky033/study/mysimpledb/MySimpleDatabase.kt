package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.table.Table
import kr.bigsky033.study.mysimpledb.table.statement.Statement
import kr.bigsky033.study.mysimpledb.table.statement.StatementType

class MySimpleDatabase(
    private val table: Table<Row>
) : Database {

    override fun execute(line: String) {
        val statement = Statement.prepareStatement(line)
        if (statement.type == StatementType.UNKNOWN) {
            println("$line is unknown statement type")
            return
        }
        table.execute(statement)
        println("Executed")
    }

}