package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.statement.Statement
import kr.bigsky033.study.mysimpledb.statement.StatementExecutor
import kr.bigsky033.study.mysimpledb.statement.StatementType

class MySimpleDatabase(
    private val table: Table<Row>,
    private val insertStatementExecutor: StatementExecutor<Row>,
    private val selectStatementExecutor: StatementExecutor<Row>
) : Database {

    override fun execute(statement: Statement) {
        val statementExecutor = selectStatementExecutor(statement.type)
        statementExecutor.execute(statement, table)
        println("Executed")
    }

    private fun selectStatementExecutor(type: StatementType) = when (type) {
        StatementType.INSERT -> insertStatementExecutor
        StatementType.SELECT -> selectStatementExecutor
        else -> throw IllegalArgumentException("Input statement is not valid")
    }

}