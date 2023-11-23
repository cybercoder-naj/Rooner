package domain

import androidx.compose.ui.graphics.Color

interface LanguageSetting {
    val name: String
    val fileExtension: String
    val executionCommand: List<String>
    val keywords: Map<String, Color>
}