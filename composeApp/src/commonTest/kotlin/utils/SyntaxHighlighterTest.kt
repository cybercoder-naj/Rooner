package utils

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SyntaxHighlighterTest {

    @Test
    fun splitWithCharacter1() {
        val (words, separators) = "val".splitBy()

        assertArrayEquals(arrayOf("val"), words.toTypedArray())
        assertTrue(separators.isEmpty())
    }

    @Test
    fun splitWithCharacter2() {
        val (words, separators) = "val x".splitBy()

        assertArrayEquals(arrayOf("val", "x"), words.toTypedArray())
        assertArrayEquals(arrayOf(' '), separators.toTypedArray())
    }

    @Test
    fun splitWithCharacter3() {
        val (words, separators) = "val x = 10\nprintln(x)".splitBy()

        assertArrayEquals(
            arrayOf(
                "val",
                "x",
                "",
                "",
                "10",
                "println",
                "x"
            ), words.toTypedArray()
        )

        assertArrayEquals(
            arrayOf(
                ' ',
                ' ',
                '=',
                ' ',
                '\n',
                '(',
                ')'
            ), separators.toTypedArray()
        )
    }

    @Test
    fun splitWithCharacter4() {
        val (words, separators) = "\nfor (i in 1..10) {\n\tprintln(i)\n}\n\n".splitBy()

        assertArrayEquals(
            arrayOf(
                "",
                "for",
                "",
                "i",
                "in",
                "1",
                "",
                "10",
                "",
                "",
                "",
                "",
                "println",
                "i",
                "",
                "",
                "",
                ""
            ), words.toTypedArray()
        )

        assertArrayEquals(
            arrayOf(
                '\n',
                ' ',
                '(',
                ' ',
                ' ',
                '.',
                '.',
                ')',
                ' ',
                '{',
                '\n',
                '\t',
                '(',
                ')',
                '\n',
                '}',
                '\n',
                '\n'
            ), separators.toTypedArray()
        )
    }

    @Test
    fun combine1() {
        val code = "val"
        val (words, separators) = code.splitBy()

        assertEquals(code, words.combine<StringBuilder>(separators).toString())
    }

    @Test
    fun combine2() {
        val code = "val x"
        val (words, separators) = code.splitBy()

        assertEquals(code, words.combine<StringBuilder>(separators).toString())
    }

    @Test
    fun combine3() {
        val code = "val x = 10\nprintln(x)"
        val (words, separators) = code.splitBy()

        assertEquals(code, words.combine<StringBuilder>(separators).toString())
    }

    @Test
    fun combine4() {
        val code = "\nfor (i in 1..10) {\n\tprintln(i)\n}\n\n"
        val (words, separators) = code.splitBy()

        assertEquals(code, words.combine<StringBuilder>(separators).toString())
    }
}