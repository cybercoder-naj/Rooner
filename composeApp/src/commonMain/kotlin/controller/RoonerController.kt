package controller

import controller.models.ProcessOutput
import kotlinx.coroutines.flow.flow

class RoonerController : Controller {
    override fun runCode(code: String) = flow<ProcessOutput> {
        TODO("Not yet implemented")
    }
}
