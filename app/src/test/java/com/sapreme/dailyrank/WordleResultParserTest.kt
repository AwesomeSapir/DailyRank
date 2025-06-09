package com.sapreme.dailyrank

import com.sapreme.dailyrank.data.parser.impl.WordleResultParser
import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WordleResultParserTest {

    private val parser = WordleResultParser()
    private val date = LocalDate.of(2025, 6, 9)

    @Test
    fun `parse success in 4 attempts`() {
        val raw = """
            Wordle 1,450 4/6

            🟨⬛⬛🟨⬛
            ⬛🟩🟩🟩⬛
            ⬛🟩🟩🟩🟩
            🟩🟩🟩🟩🟩
        """.trimIndent()

        val result = parser.parse(raw, date)
        assertNotNull(result)
        with(result) {
            assertEquals(1450, puzzleId)
            assertTrue(succeeded)
            assertEquals(4, attempts)
            assertEquals(date, this.date)
        }
    }

    @Test
    fun `parse success in 3 attempts with leading space`() {
        val raw = """
             Wordle 1,450 3/6

             ⬜🟩🟩🟩⬜
             🟩🟩🟩🟩⬜
             🟩🟩🟩🟩🟩
        """.trimIndent()

        val result = parser.parse(raw, date)
        assertNotNull(result)
        with(result) {
            assertEquals(1450, puzzleId)
            assertTrue(succeeded)
            assertEquals(3, attempts)
            assertEquals(date, this.date)
        }
    }

    @Test
    fun `parse success in 6 attempts`() {
        val raw = """
            Wordle 1,448 6/6

            ⬜🟨⬜⬜⬜
            ⬜🟨⬜⬜⬜
            ⬜⬜🟩⬜⬜
            🟨🟨⬜🟨🟨
            🟨🟨🟩⬜🟩
            🟩🟩🟩🟩🟩
        """.trimIndent()

        val result = parser.parse(raw, date)
        assertNotNull(result)
        with(result) {
            assertEquals(1448, puzzleId)
            assertTrue(succeeded)
            assertEquals(6, attempts)
            assertEquals(date, this.date)
        }
    }

    @Test
    fun `parse failure (X) treated as 6 attempts`() {
        val raw = """
            Wordle 1,447 X/6

            ⬜⬜🟨⬜🟨
            🟨⬜⬜⬜⬜
            🟨⬜🟩⬜⬜
            ⬜🟩🟨⬜🟨
            ⬜🟩⬜⬜⬜
            🟨⬜⬜⬜⬜
        """.trimIndent()

        val result = parser.parse(raw, date)
        assertNotNull(result)
        with(result) {
            assertEquals(1447, puzzleId)
            assertFalse(succeeded)
            assertEquals(6, attempts)  // X => 6
            assertEquals(date, this.date)
        }
    }

    @Test
    fun `invalid input returns null`() {
        assertNull(parser.parse("Some random text", LocalDate.now()))
    }

}