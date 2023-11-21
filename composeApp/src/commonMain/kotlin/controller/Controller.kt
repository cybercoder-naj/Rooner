package controller

import controller.models.ProcessOutput
import kotlinx.coroutines.flow.Flow

interface Controller {
    fun runCode(code: String): Flow<ProcessOutput>
}