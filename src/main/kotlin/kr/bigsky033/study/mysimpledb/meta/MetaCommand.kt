package kr.bigsky033.study.mysimpledb.meta

import kotlin.system.exitProcess


enum class MetaCommand(val value: String) {

    EXIT(".exit") {
        override fun execute() {
            exitProcess(1)
        }
    },

    UNKNOWN("") {
        override fun execute() {
            // EMPTY
        }
    };

    companion object {

        private val constants = mutableMapOf<String, MetaCommand>()

        init {
            values().forEach { command ->
                constants[command.value] = command
            }
        }

        fun contains(s: String?): Boolean = s != null && constants[s] != null

        fun notContains(s: String?) = !contains(s)

        fun fromValue(s: String) = constants[s.toLowerCase()] ?: UNKNOWN

        fun isMetaCommand(command: String?) = command != null && command.startsWith('.')

    }

    abstract fun execute()

}

