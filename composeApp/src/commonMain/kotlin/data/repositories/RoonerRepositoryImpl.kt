package data.repositories

import data.executables.KotlinExecutable
import data.models.ProcessOutput
import domain.FileIO
import domain.RoonerRepository
import kotlinx.coroutines.flow.flow
import java.io.File

class RoonerRepositoryImpl : RoonerRepository {
    override fun runCode(code: String) = flow {
        val executable = KotlinExecutable()
        val fileIo = FileIO(executable)

        emit(ProcessOutput.OutputString("Writing to file"))
        fileIo.writeToFile(code)
        emit(ProcessOutput.OutputString("Executing code. . .\n"))
        emit(ProcessOutput.OutputString("Executing code. . .\n"))

        emit(ProcessOutput.Complete(0))
    }
}
