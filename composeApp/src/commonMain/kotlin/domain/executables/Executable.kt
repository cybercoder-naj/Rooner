package domain.executables

interface Executable {
    val name: String
    val fileExtension: String
    val executionCommand: List<String>
}