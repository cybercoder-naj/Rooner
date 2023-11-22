package domain

interface Executable {
    val name: String
    val fileExtension: String
    val executionCommand: List<String>
}