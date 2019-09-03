package kr.bigsky033.study.mysimpledb.table

import kr.bigsky033.study.mysimpledb.table.statement.Statement

interface Table<T> {

    fun execute(statement: Statement)

}