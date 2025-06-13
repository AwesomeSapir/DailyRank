package com.sapreme.dailyrank

import com.sapreme.dailyrank.data.parser.impl.ConnectionsResultParser
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ConnectionsResultParserTest {

    private val parser = ConnectionsResultParser()
    private val date = LocalDate.of(2025, 6, 9)

    @Test
    fun `perfect solve 4 rows succeeds with zero mistakes`() {
        val raw = """
            Connections
            Puzzle #728
            🟨🟨🟨🟨
            🟩🟩🟩🟩
            🟦🟦🟦🟦
            🟪🟪🟪🟪
        """.trimIndent()

        val result = parser.parse(raw, date)
        assertNotNull(result)
        with(result) {
            assertEquals(728, puzzleId)
            assertTrue(succeeded)
            assertEquals(4, attempts)
            assertEquals(0, mistakes)
            assertEquals(date, this.date)
        }
    }

    @Test
    fun `failed solve with 6 rows yields false succeeded and 4 mistakes`() {
        val raw = """
            Connections
            Puzzle #728
            🟨🟩🟨🟨
            🟨🟨🟩🟨
            🟨🟨🟨🟨
            🟩🟪🟩🟩
            🟩🟩🟩🟩
            🟪🟦🟪🟦
        """.trimIndent()

        val result = parser.parse(raw, date)
        assertNotNull(result)
        with(result) {
            assertEquals(728, puzzleId)
            assertFalse(succeeded)
            assertEquals(6, attempts)
            assertEquals(4, mistakes)
            assertEquals(date, this.date)
        }
    }

    @Test
    fun `solve in 5 rows with one mistake succeeds with one mistake`() {
        val raw = """
            Connections
            Puzzle #727
            🟨🟨🟨🟨
            🟪🟩🟩🟩
            🟩🟩🟩🟩
            🟪🟪🟪🟪
            🟦🟦🟦🟦
        """.trimIndent()

        val result = parser.parse(raw, date)
        assertNotNull(result)
        with(result) {
            assertEquals(727, puzzleId)
            assertTrue(succeeded)
            assertEquals(5, attempts)
            assertEquals(1, mistakes)
            assertEquals(date, this.date)
        }
    }

    @Test
    fun `failed archive solve with 5 rows yields false succeeded and 4 mistakes`() {
        val raw = """
            Connections Puzzle #731
            🟦🟨🟨🟨
            🟨🟨🟨🟨
            🟩🟦🟪🟦
            🟪🟦🟦🟩
            🟦🟪🟦🟦
        """.trimIndent()

        val result = parser.parse(raw, date)
        assertNotNull(result)
        with(result) {
            assertEquals(731, puzzleId)
            assertFalse(succeeded)
            assertEquals(5, attempts)
            assertEquals(1, groupings)
            assertEquals(4, mistakes)
            assertEquals(date, this.date)
        }
    }

    @Test
    fun `invalid header returns null`() {
        val raw = """
            Puzzle #700
            🟨🟨🟨🟨
        """.trimIndent()

        assertNull(parser.parse(raw, date))
    }

    @Test
    fun `too few lines returns null`() {
        val raw = "Connections\nPuzzle #700"
        assertNull(parser.parse(raw, date))
    }

}