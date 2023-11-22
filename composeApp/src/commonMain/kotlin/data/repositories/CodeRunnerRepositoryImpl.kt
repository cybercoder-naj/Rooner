package data.repositories

import domain.executables.Executable
import domain.models.ProcessOutput
import domain.repositories.CodeRunnerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Paths
import java.util.Scanner
import java.util.concurrent.TimeUnit

/*
 * TODO this class is coupled with the details of files and processes.
 *      which makes it difficult to test. Need to abstract this away.
 */
class CodeRunnerRepositoryImpl(
    private val executable: Executable
) : CodeRunnerRepository {
    override fun runCode(code: String) = flow {
        emit(ProcessOutput.OutputString("Uploading the script. . ."))
        val path = Paths.get(System.getenv("HOME"), ".cache")
        val file = File(path.toString(), "script.${executable.fileExtension}")
        try {
            if (!file.exists())
                file.createNewFile()

            file.writeText(code)
            file.deleteOnExit()
        } catch (ioe: IOException) {
            emit(ProcessOutput.ErrorString("Application faulted while uploading the script: ${ioe.message}"))
            emit(ProcessOutput.Complete(1)) // TODO replace with constant
            return@flow
        }

        emit(ProcessOutput.OutputString("Executing the script. . .\n"))
        val process: Process
        try {
            process = ProcessBuilder(
                executable.executionCommand + file.absolutePath
            ).start()
        } catch (ioe: IOException) {
            emit(ProcessOutput.ErrorString("Application faulted while executing the script: ${ioe.message}"))
            emit(ProcessOutput.Complete(1))
            return@flow
        }

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

        try {
            process.waitFor(30L, TimeUnit.SECONDS);
        } catch (ie: InterruptedException) {
            emit(ProcessOutput.ErrorString("Script took longer than 30 seconds to execute."))
            emit(ProcessOutput.Complete(1))
            return@flow
        }

        emit(ProcessOutput.Complete(process.exitValue()))
    }.flowOn(Dispatchers.IO)
}
