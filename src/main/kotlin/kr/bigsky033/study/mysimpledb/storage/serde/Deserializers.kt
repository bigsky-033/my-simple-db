package kr.bigsky033.study.mysimpledb.storage.serde

import kr.bigsky033.study.mysimpledb.entity.Row
import kr.bigsky033.study.mysimpledb.validation.validateForRow

fun String.toRow(delimiter: String): Row {
    val tokens = split(delimiter)
    if (tokens.size != 3) {
        throw IllegalArgumentException("")
    }
    val id = tokens[0].toIntOrNull()
        ?: throw IllegalArgumentException("first token is not number. token: ${tokens[0]}")
    val username = tokens[1]
    val email = tokens[2]

    validateForRow(id = id, username = username, email = email)
    return Row(id = id, username = username, email = email)
}
