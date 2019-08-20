package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.statement.InsertStatementExecutor
import kr.bigsky033.study.mysimpledb.statement.SelectStatementExecutor
import kr.bigsky033.study.mysimpledb.storage.SimpleListBasedStorage
import kr.bigsky033.study.mysimpledb.storage.ds.SimpleLinkedList

class ApplicationInitializer {

    fun initializeDatabase(): Database {
        val simpleLinkedList = SimpleLinkedList<Row>()
        val storage = SimpleListBasedStorage(rows = simpleLinkedList, maxRows = 100)

        val insertStatementExecutor = InsertStatementExecutor()
        val selectStatementExecutor = SelectStatementExecutor()

        return MySimpleDatabase(
            storage = storage,
            insertStatementExecutor = insertStatementExecutor,
            selectStatementExecutor = selectStatementExecutor
        )
    }

}