package data.repositories

import data.executables.KotlinExecutable
import data.models.ProcessOutput
import domain.CodeRunner
import domain.RoonerRepository
import kotlinx.coroutines.flow.flow

class RoonerRepositoryImpl : RoonerRepository {
    override fun runCode(code: String) = flow {
        val executable = KotlinExecutable()

        val codeRunner = CodeRunner(executable)
        emit(ProcessOutput.OutputString("Executing code. . .\n"))

        codeRunner.executeCode(
            code = code,
        ).collect(::emit)

        emit(ProcessOutput.Complete(0))
    }
}
