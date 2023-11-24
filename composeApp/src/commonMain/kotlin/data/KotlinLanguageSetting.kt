package data

import androidx.compose.ui.graphics.Color
import domain.LanguageSetting
import utils.FILENAME

class KotlinLanguageSetting : LanguageSetting {
    override val name: String
        get() = "Kotlin"
    override val fileExtension: String
        get() = "kts"
    override val filename: String
        get() = "$FILENAME.$fileExtension"
    override val executionCommand: List<String>
        get() = "kotlinc -script".split(" ")
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
        ).associateWith { Color(0xFFFFF800) }

}