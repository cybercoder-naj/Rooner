package domain

import java.io.File
import java.nio.file.Paths

class FileIO(
    private val executable: Executable
) {
    fun writeToFile(text: String): File {
        val path = Paths.get(System.getenv("HOME"), ".cache")
        val file = File(path.toString(), "rooner.${executable.fileExtension}")
        file.writeText(text)
        file.deleteOnExit()

        return file
    }
}