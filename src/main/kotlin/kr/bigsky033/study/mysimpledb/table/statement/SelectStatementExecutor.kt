package kr.bigsky033.study.mysimpledb.table.statement

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.table.Cursor

class SelectStatementExecutor(
    private val cursor: Cursor<Row>,
    private val statement: Statement
) : StatementExecutor<Row> {

    override fun execute() {
        if (statement !is SelectStatement) throw IllegalArgumentException("input statement is not select statement")

        var row: Row?
        if (cursor.hasNext()) {
            row = cursor.next()
        } else {
            println("empty")
            return
        }

        while (row != null) {
            println(row)
            row = cursor.next()
        }
    }

}