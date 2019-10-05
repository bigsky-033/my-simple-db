package kr.bigsky033.study.mysimpledb.table.statement

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.table.Cursor

class SelectStatementExecutor(
    private val cursor: Cursor<Row>,
    private val statement: Statement
) : StatementExecutor<Row> {

    override fun execute() {
        if (statement !is SelectStatement) throw IllegalArgumentException("input statement is not select statement")
        val content = statement.content.trim()
        if (content.contains("select") || content.isBlank()) {
            throw IllegalArgumentException("please check your input. current input: $content")
        }

        if (content == "*") {
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
        } else {
            val id = content.toInt()

            val row = cursor.get(id)
            if (row != null) {
                println(row)
            } else {
                println("not found")
            }
        }

    }

}