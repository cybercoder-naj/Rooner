package domain

import androidx.compose.ui.graphics.Color

// Improvement: Add documentation, although fairly simple
interface LanguageSetting {
    val name: String
    val fileExtension: String
    val filename: String
    val baseCommand: String
    val executionCommand: List<String>
    val keywords: Map<String, Color>
}