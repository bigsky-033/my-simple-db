package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.statement.Statement

interface Database {

    fun execute(statement: Statement)

}