package domain.models

/*
 * TODO flag this with David. I am coupling core business logic with this class,
 *      but i don't know how to workaround this issue.
 */
sealed class ProcessOutput {
    data class OutputString(val message: String): ProcessOutput()
    data class ErrorString(val message: String): ProcessOutput()
    data class Complete(val status: Int): ProcessOutput()
}
