package kr.bigsky033.study.mysimpledb.statement

open class Statement(val type: StatementType) {

    companion object {

        fun prepareStatement(s: String): Statement {
            return when (val type = StatementType.fromValue(s.substringBefore(" "))) {
                StatementType.INSERT -> InsertStatement(s.substringAfter(" "))
                StatementType.SELECT -> SelectStatement()
                else -> throw IllegalArgumentException("input string is not valid input to prepare statement. Input string: $s")
            }
        }

    }

}