package utils

fun String.splitBy(
    predicate: (Char) -> Boolean = Char::isLetterOrDigit
): Pair<List<String>, List<Char>> {
    val words = mutableListOf<String>()
    val separators = mutableListOf<Char>()

    val word = StringBuilder()
    for (ch in this) {
        if (predicate(ch))
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

fun <T : Appendable> List<CharSequence>.combine(
    separators: List<Char>,
    builder: () -> T = {
        @Suppress("UNCHECKED_CAST")
        StringBuilder() as T
    }
): T {
    return builder().apply {
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