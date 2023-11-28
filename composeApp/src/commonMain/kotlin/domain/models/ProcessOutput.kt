package domain.models

sealed class ProcessOutput {
    data class StdOut(val message: String) : ProcessOutput()
    data class StdErr(val message: String) : ProcessOutput()
    data class Exit(val status: Int) : ProcessOutput()
}
