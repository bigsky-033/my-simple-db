package kr.bigsky033.study.mysimpledb.validation

fun validateForRow(id: Int, username: String, email: String) {
    if (id < 1) throw IllegalArgumentException("id cannot be negative value. your input: $id")
    if (username.length > 32) throw IllegalArgumentException("exceeds max length of username. your input: $username")
    if (email.length > 256) throw IllegalArgumentException("exceeds max length of email. your input: $email")
}