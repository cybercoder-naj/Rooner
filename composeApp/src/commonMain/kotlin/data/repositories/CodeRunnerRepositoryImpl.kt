package data.repositories

import domain.LanguageSetting
import domain.OsInformation
import domain.models.ProcessOutput
import domain.repositories.CodeRunnerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import utils.ExitValue
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.Scanner
import java.util.concurrent.TimeUnit

class CodeRunnerRepositoryImpl(
    private val languageSetting: LanguageSetting,
    private val osInformation: OsInformation
) : CodeRunnerRepository {
    override fun runCode(code: String) = flow {
        emit(ProcessOutput.StdOut("Uploading the script. . ."))
        val path = osInformation.getCachePath()
        val file = File(path.toString(), languageSetting.filename)

        try {
            if (!file.exists())
                file.createNewFile()

            file.writeText(code)
            file.deleteOnExit()
        } catch (ioe: IOException) {
            emit(ProcessOutput.StdErr("Application faulted while uploading the script: ${ioe.message}"))
            emit(ProcessOutput.Exit(ExitValue.FAILURE))
            return@flow
        }

        emit(ProcessOutput.StdOut("Executing the script. . .\n"))
        val process: Process
        try {
            process = ProcessBuilder(
                languageSetting.executionCommand + file.absolutePath
            ).start()
        } catch (ioe: IOException) {
            emit(ProcessOutput.StdErr("Application faulted while executing the script: ${ioe.message}"))
            emit(ProcessOutput.Exit(ExitValue.FAILURE))
            return@flow
        }

        InputStreamReader(process.inputStream).use {
            Scanner(it).use { scanner ->
                while (scanner.hasNextLine()) {
                    emit(ProcessOutput.StdOut(scanner.nextLine()))
                }
            }
        }

        InputStreamReader(process.errorStream).use {
            Scanner(it).use { scanner ->
                while (scanner.hasNextLine()) {
                    emit(ProcessOutput.StdErr(scanner.nextLine()))
                }
            }
        }

        try {
            process.waitFor(30L, TimeUnit.SECONDS);
        } catch (ie: InterruptedException) {
            emit(ProcessOutput.StdErr("Script took longer than 30 seconds to execute."))
            emit(ProcessOutput.Exit(ExitValue.FAILURE))
            return@flow
        }

        emit(ProcessOutput.Exit(process.exitValue()))
    }.flowOn(Dispatchers.IO)
}
