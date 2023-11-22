package data.executables

import domain.executables.Executable

class KotlinExecutable : Executable {
    override val name: String
        get() = "Kotlin"
    override val fileExtension: String
        get() = "kts"
    override val executionCommand: List<String>
        get() = "kotlinc -script".split(" ")
}