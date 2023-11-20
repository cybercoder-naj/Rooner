package controller

import controller.models.OutputString
import kotlinx.coroutines.flow.Flow

interface Controller {
    fun runCode(code: String): Flow<OutputString>
}