package controller.models

sealed class ExitStatus(val status: Int? = null) {
    data object Success : ExitStatus(0)
    class Failure(status: Int) : ExitStatus(status)
    data object None : ExitStatus()
}
