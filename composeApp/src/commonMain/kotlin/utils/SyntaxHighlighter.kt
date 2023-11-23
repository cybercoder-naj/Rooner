package utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import domain.LanguageSetting

fun highlight(code: String, languageSetting: LanguageSetting): AnnotatedString {
    val (words, separators) = code.splitWithCharacter()

    val annotatedWords = words.map {
        buildAnnotatedString {
            if (it in languageSetting.keywords)
                withStyle(style = SpanStyle(Color.Yellow)) {
                    append(it)
                }
            else append(it)
        }
    }

    return annotatedWords.combine(separators)
}

fun String.splitWithCharacter(): Pair<List<String>, List<Char>> {
    val words = mutableListOf<String>()
    val separators = mutableListOf<Char>()

    val word = StringBuilder()
    for (ch in this) {
        if (ch.isLetterOrDigit())
            word.append(ch)
        else {
            words.add(word.toString())
            word.clear()
            separators.add(ch)
        }
    }
    if (word.isNotBlank())
        words.add(word.toString())

    return words to separators
}

fun List<CharSequence>.combine(separators: List<Char>): AnnotatedString {
    return buildAnnotatedString {
        var p1 = 0
        var p2 = 0
        while (p1 < this@combine.size && p2 < separators.size) {
            append(this@combine[p1])
            append(separators[p2])
            p1++
            p2++
        }

        while (p1 < this@combine.size) {
            append(this@combine[p1])
            p1++
        }

        while (p2 < separators.size) {
            append(separators[2])
            p2++
        }
    }
}