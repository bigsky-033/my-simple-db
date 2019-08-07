package kr.bigsky033.study.mysimpledb.statement

import kr.bigsky033.study.mysimpledb.storage.Storage


class SelectStatementExecutor : StatementExecutor {

    override fun execute(statement: Statement, storage: Storage) {
        if (statement !is SelectStatement) throw IllegalArgumentException("input statement is not select statement")

        val rows = storage.getRows()
        if (rows.count() < 1) {
            println("Empty")
            return
        }

        var node = rows.first()
        while (node != null) {
            println(node.value)
            node = node.next
        }
    }

}