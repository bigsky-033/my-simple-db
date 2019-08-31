package kr.bigsky033.study.mysimpledb.statement

import kr.bigsky033.study.mysimpledb.Table

interface StatementExecutor<T> {

    fun execute(statement: Statement, table: Table<T>)

}