package data

import domain.Logger

class StdoutLogger : Logger {
    override fun log(message: String) {
        println(message)
    }

}
