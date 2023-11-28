package data

import androidx.compose.ui.graphics.Color
import domain.LanguageSetting
import utils.Constants.FILENAME

class KotlinLanguageSetting : LanguageSetting {
    override val name: String
        get() = "Kotlin"
    override val fileExtension: String
        get() = "kts"
    override val filename: String
        get() = "$FILENAME.$fileExtension"
    override val baseCommand: String
        get() = "kotlinc"
    override val executionCommand: List<String>
        get() = "$baseCommand -script".split(" ")
    override val keywords: Map<String, Color>
        get() = listOf(
            "as",
            "as?",
            "break",
            "class",
            "continue",
            "do",
            "else",
            "false",
            "for",
            "fun",
            "if",
            "in",
            "!in",
            "is",
            "!is",
            "null",
            "object",
            "package",
            "return",
            "super",
            "this",
            "throw",
            "true",
            "typealias",
            "typeof",
            "val",
            "var",
            "when",
            "while"
        ).associateWith { Color.Yellow }

}