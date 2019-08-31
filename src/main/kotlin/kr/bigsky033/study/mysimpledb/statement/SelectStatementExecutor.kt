package kr.bigsky033.study.mysimpledb.statement

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.Table

class SelectStatementExecutor : StatementExecutor<Row> {

    override fun execute(statement: Statement, table: Table<Row>) {
        if (statement !is SelectStatement) throw IllegalArgumentException("input statement is not select statement")

        val rows = table.selectAll()
        if (rows.size() < 1) {
            println("Empty")
            return
        }

        rows.forEach { row ->
            println(row)
        }
    }

}