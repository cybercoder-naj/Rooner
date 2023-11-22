package data.repositories

import data.executables.KotlinExecutable
import domain.models.ProcessOutput
import domain.CodeRunner
import domain.repositories.RoonerRepository
import kotlinx.coroutines.flow.flow

class RoonerRepositoryImpl : RoonerRepository {
    override fun runCode(code: String) = flow {
        val executable = KotlinExecutable()

        val codeRunner = CodeRunner(executable)
        emit(ProcessOutput.OutputString("Building kotlin script. . .\n"))

        codeRunner.executeCode(
            code = code,
        ).collect(::emit)

        emit(ProcessOutput.Complete(0))
    }
}
