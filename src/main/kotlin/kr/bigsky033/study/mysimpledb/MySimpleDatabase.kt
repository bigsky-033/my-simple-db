package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.statement.Statement
import kr.bigsky033.study.mysimpledb.statement.StatementExecutor
import kr.bigsky033.study.mysimpledb.statement.StatementType
import kr.bigsky033.study.mysimpledb.storage.Storage


class MySimpleDatabase(
    private val storage: Storage,
    private val insertStatementExecutor: StatementExecutor,
    private val selectStatementExecutor: StatementExecutor
) : Database {

    override fun execute(statement: Statement) {
        val statementExecutor = selectStatementExecutor(statement.type)
        statementExecutor.execute(statement, storage)
        println("Executed")
    }

    private fun selectStatementExecutor(type: StatementType) = when (type) {
        StatementType.INSERT -> insertStatementExecutor
        StatementType.SELECT -> selectStatementExecutor
        else -> throw IllegalArgumentException("Input statement is not valid")
    }

}