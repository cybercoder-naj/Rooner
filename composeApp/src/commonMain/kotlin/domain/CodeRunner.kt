package domain

import domain.executables.Executable
import domain.models.ProcessOutput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Paths
import java.util.Scanner

class CodeRunner(
    private val executable: Executable,
) {
    fun executeCode(
        code: String,
    ) = flow {
        val path = Paths.get(System.getenv("HOME"), ".cache")
        val file = File(path.toString(), "script.${executable.fileExtension}")
        if (!file.exists())
            file.createNewFile()

        file.writeText(code)
        file.deleteOnExit()

        val process = ProcessBuilder(
            executable.executionCommand + file.absolutePath
        ).start()

        InputStreamReader(process.inputStream).use {
            Scanner(it).use { scanner ->
                while (scanner.hasNextLine()) {
                    emit(ProcessOutput.OutputString(scanner.nextLine()))
                }
            }
        }

        InputStreamReader(process.errorStream).use {
            Scanner(it).use { scanner ->
                while (scanner.hasNextLine()) {
                    emit(ProcessOutput.ErrorString(scanner.nextLine()))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}