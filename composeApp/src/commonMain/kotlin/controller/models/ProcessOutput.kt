package controller.models

sealed class ProcessOutput {
    data class OutputString(val message: String): ProcessOutput()
    data class ErrorString(val message: String): ProcessOutput()
    data class Complete(val status: Int): ProcessOutput()
}
