package data

import domain.LanguageSetting

class KotlinLanguageSetting : LanguageSetting {
    override val name: String
        get() = "Kotlin"
    override val fileExtension: String
        get() = "kts"
    override val executionCommand: List<String>
        get() = "kotlinc -script".split(" ")
}