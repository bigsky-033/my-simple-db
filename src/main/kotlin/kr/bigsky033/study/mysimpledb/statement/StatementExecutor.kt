package kr.bigsky033.study.mysimpledb.statement

import kr.bigsky033.study.mysimpledb.storage.Storage


interface StatementExecutor {

    fun execute(statement: Statement, storage: Storage)

}