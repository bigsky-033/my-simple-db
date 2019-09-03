package kr.bigsky033.study.mysimpledb.table

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.Storage
import kr.bigsky033.study.mysimpledb.table.statement.InsertStatementExecutor
import kr.bigsky033.study.mysimpledb.table.statement.SelectStatementExecutor
import kr.bigsky033.study.mysimpledb.table.statement.Statement
import kr.bigsky033.study.mysimpledb.table.statement.StatementType

class SimpleTableForRow(
    private val storage: Storage<Row>,
    // TODO
    private val maxDataSize: Int
) : Table<Row> {

    override fun execute(statement: Statement) {
        val statementExecutor = selectStatementExecutor(statement)
        statementExecutor.execute()
    }

    private fun selectStatementExecutor(statement: Statement) = when (statement.type) {
        StatementType.INSERT -> InsertStatementExecutor(SimpleCursorForRow(storage), statement)
        StatementType.SELECT -> SelectStatementExecutor(SimpleCursorForRow(storage), statement)
        else -> throw IllegalArgumentException("Input statement is not valid")
    }

}