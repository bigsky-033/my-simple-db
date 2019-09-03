package kr.bigsky033.study.mysimpledb.table.statement

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.serde.toRow
import kr.bigsky033.study.mysimpledb.table.Cursor

class InsertStatementExecutor(
    private val cursor: Cursor<Row>,
    private val statement: Statement
) : StatementExecutor<Row> {

    override fun execute() {
        if (statement !is InsertStatement) throw IllegalArgumentException("input statement is not insert statement")
        val row = statement.content.toRow(" ")
        cursor.add(row)
    }

}