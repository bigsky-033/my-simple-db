package kr.bigsky033.study.mysimpledb.statement

import kr.bigsky033.study.mysimpledb.Table
import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.serde.toRow

class InsertStatementExecutor : StatementExecutor<Row> {

    override fun execute(statement: Statement, table: Table<Row>) {
        if (statement !is InsertStatement) throw IllegalArgumentException("input statement is not insert statement")
        val row = statement.content.toRow(" ")
        table.insert(row)
    }

}