package controller

import controller.models.OutputString
import kotlinx.coroutines.flow.flow

class RoonerController : Controller {
    override fun runCode(code: String) = flow<OutputString> {
        TODO("Not yet implemented")
    }
}
