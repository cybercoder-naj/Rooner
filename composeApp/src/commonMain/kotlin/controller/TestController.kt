package controller

import controller.models.ProcessOutput
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class TestController : Controller {
    override fun runCode(code: String) = flow {
        for (i in 1..3) {
            emit(ProcessOutput.OutputString("Running process, stdout output!"))
            delay(500L)
            emit(ProcessOutput.ErrorString("Running process, stderr output!"))
            delay(500L)
        }

        emit(ProcessOutput.Complete(0))
    }
}
