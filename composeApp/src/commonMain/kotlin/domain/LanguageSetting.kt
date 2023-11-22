package domain

interface LanguageSetting {
    val name: String
    val fileExtension: String
    val executionCommand: List<String>
}