package kr.bigsky033.study.mysimpledb.table.statement

enum class StatementType {

    INSERT,

    SELECT,

    UNKNOWN;

    companion object {

        fun fromValue(s: String): StatementType {
            return try {
                valueOf(s.toUpperCase())
            } catch (ex: IllegalArgumentException) {
                UNKNOWN
            }
        }

    }

}
