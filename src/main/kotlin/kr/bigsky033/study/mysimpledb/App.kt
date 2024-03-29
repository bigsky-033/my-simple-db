package kr.bigsky033.study.mysimpledb

import kr.bigsky033.study.mysimpledb.meta.MetaCommand

class App {

    private val database: Database

    init {
        val context = ApplicationContext()
        database = context.initialize()
        Runtime.getRuntime().addShutdownHook(Thread {
            context.terminate()
        })
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
                database.execute(line)
            } catch (ex: IllegalArgumentException) {
                println("Your input is not valid. Please check your input again.")
                println("- Details: ${ex.message}")
            } catch (ex: IllegalStateException) {
                println("Currently database is not valid status.")
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
