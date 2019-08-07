package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.statement.InsertStatementExecutor
import kr.bigsky033.study.mysimpledb.statement.SelectStatementExecutor
import kr.bigsky033.study.mysimpledb.statement.Statement
import kr.bigsky033.study.mysimpledb.statement.StatementType
import kr.bigsky033.study.mysimpledb.storage.StorageImpl


class MySimpleDatabase : Database {

    private val storage = StorageImpl()

    private val insertStatementExecutor = InsertStatementExecutor()

    private val selectStatementExecutor = SelectStatementExecutor()

    override fun execute(statement: Statement) {
        val statementExecutor = when (statement.type) {
            StatementType.INSERT -> this.insertStatementExecutor
            StatementType.SELECT -> this.selectStatementExecutor
            else -> throw IllegalArgumentException("Input statement is not valid")
        }
        statementExecutor.execute(statement, this.storage)
        println("Executed")
    }

}