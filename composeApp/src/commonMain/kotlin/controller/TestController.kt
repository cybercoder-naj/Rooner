package controller

import controller.models.OutputString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class TestController : Controller {
    override fun runCode(code: String) = flow {
        emit(OutputString.StdOut("Running 1!"))
        delay(2000L)
        emit(OutputString.StdErr("Running 2!"))
    }
}
