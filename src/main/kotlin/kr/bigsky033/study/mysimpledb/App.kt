package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.meta.MetaCommand
import kr.bigsky033.study.mysimpledb.statement.Statement
import kr.bigsky033.study.mysimpledb.statement.StatementType

class App {

    private val database: Database

    init {
        val initializer = ApplicationInitializer()
        database = initializer.initializeDatabase()
    }

    fun run() {
        while (true) {
            printPrompt()
            val line = readLine()
                ?: throw IllegalArgumentException("input line should not be null but current input line is null")

            if (MetaCommand.isMetaCommand(line)) {
                val command = MetaCommand.fromValue(line)
                if (command != MetaCommand.UNKNOWN) {
                    command.execute()
                } else {
                    println("$line is unknown meta command")
                }
                continue
            }

            try {
                val statement = Statement.prepareStatement(line)
                if (statement.type == StatementType.UNKNOWN) {
                    println("$line is unknown statement type")
                    continue
                }
                database.execute(statement)
            } catch (ex: IllegalArgumentException) {
                println("Your input is not valid. Please check your input again.")
                println("- Details: ${ex.message}")
            }
        }
    }

    private fun printPrompt() {
        print("db >")
    }

}

fun main() {
    val app = App()
    app.run()
}
