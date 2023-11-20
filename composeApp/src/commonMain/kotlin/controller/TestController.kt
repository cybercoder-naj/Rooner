package controller

import controller.models.OutputString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class TestController : Controller {
    override fun runCode(code: String) = flow {
        for (i in 1..5) {
            emit(OutputString.StdOut("Running process, stdout output!"))
            delay(2000L)
            emit(OutputString.StdErr("Running process, stderr output!"))
            delay(2000L)
        }
    }
}
