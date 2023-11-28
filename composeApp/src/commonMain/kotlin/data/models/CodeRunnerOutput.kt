package data.models

import domain.models.ProcessOutput

sealed class CodeRunnerOutput {
    data class OutputString(val message: String): CodeRunnerOutput()
    data class ErrorString(val message: String): CodeRunnerOutput()
    data class Complete(val status: Int): CodeRunnerOutput()

    companion object {
        fun fromProcessOutput(processOutput: ProcessOutput): CodeRunnerOutput {
            return when(processOutput) {
                is ProcessOutput.Exit -> Complete(processOutput.status)
                is ProcessOutput.StdErr -> ErrorString(processOutput.message)
                is ProcessOutput.StdOut -> OutputString(processOutput.message)
            }
        }
    }
}
