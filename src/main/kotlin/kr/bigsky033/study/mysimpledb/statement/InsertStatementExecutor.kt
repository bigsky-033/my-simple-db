package kr.bigsky033.study.mysimpledb.statement

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.storage.Storage

class InsertStatementExecutor : StatementExecutor {

    override fun execute(statement: Statement, storage: Storage) {
        if (statement !is InsertStatement) throw IllegalArgumentException("input statement is not insert statement")
        val tokens = statement.content.split(" ")
        if (tokens.size != 3) throw IllegalArgumentException("input argument does not contains complete content for Row")

        val id = tokens[0].toIntOrNull()
            ?: throw IllegalArgumentException("first token is not number. token: ${tokens[0]}")
        val username = tokens[1]
        val email = tokens[2]
        validateInputs(id = id, username = username, email = email)

        storage.addRow(Row(id = id, username = tokens[1], email = tokens[2]))
    }

    private fun validateInputs(id: Int, username: String, email: String) {
        if (id < 1) throw IllegalArgumentException("id cannot be negative value. your input: $id")
        if (username.length > 32) throw IllegalArgumentException("exceeds max length of username. your input: $username")
        if (email.length > 256) throw IllegalArgumentException("exceeds max length of email. your input: $email")
    }

}