package data.executables

import domain.Executable

class KotlinExecutable : Executable {
    override val name: String
        get() = "Kotlin"
    override val fileExtension: String
        get() = "kt"
    override val executionCommand: String
        get() = "kotlinc -script"
}