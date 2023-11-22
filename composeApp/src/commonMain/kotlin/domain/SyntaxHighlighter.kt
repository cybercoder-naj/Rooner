package domain

import androidx.compose.ui.text.AnnotatedString

interface SyntaxHighlighter {
    fun highlight(code: String): AnnotatedString
}