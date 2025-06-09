package com.sapreme.dailyrank

import com.sapreme.dailyrank.data.parser.impl.StrandsResultParser
import com.sapreme.dailyrank.data.parser.impl.WordleResultParser
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.*

class StrandsResultParserTest {

    private val parser = StrandsResultParser()
    private val date = LocalDate.of(2025, 6, 9)

    @Test
    fun `example 462 with one hint and six words`() {
        val raw = """
            Strands #462
            “On Broadway”
            💡🔵🔵🟡
            🔵🔵🔵🔵
        """.trimIndent()
        val r = parser.parse(raw, date)!!
        assertEquals(462, r.puzzleId)
        assertEquals("On Broadway", r.title)
        assertEquals(1, r.hints)
        assertEquals(0, r.doubleHints)
        assertEquals(6, r.words)
        assertEquals(date, r.date)
    }

    @Test
    fun `example 461 with one hint and six words`() {
        val raw = """
            Strands #461
            “I am what I am”
            💡🔵🔵🔵
            🔵🔵🟡🔵
        """.trimIndent()
        val r = parser.parse(raw, date)!!
        assertEquals(461, r.puzzleId)
        assertEquals("I am what I am", r.title)
        assertEquals(1, r.hints)
        assertEquals(0, r.doubleHints)
        assertEquals(6, r.words)
    }

    @Test
    fun `example 462 with two hints and zero double‐hints`() {
        val raw = """
            Strands #462
            “On Broadway”
            💡🔵💡🔵
            🔵🟡🔵🔵
            🔵
        """.trimIndent()
        val r = parser.parse(raw, date)!!
        assertEquals(462, r.puzzleId)
        assertEquals("On Broadway", r.title)
        assertEquals(2, r.hints)
        assertEquals(0, r.doubleHints)
        assertEquals(6, r.words)
    }

    @Test
    fun `example 457 non‐hint solve`() {
        val raw = """
            Strands #457
            “That's proprietary!”
            🔵🔵🟡🔵
            🔵🔵
        """.trimIndent()
        val r = parser.parse(raw, date)!!
        assertEquals(457, r.puzzleId)
        assertEquals("That's proprietary!", r.title)
        assertEquals(0, r.hints)
        assertEquals(0, r.doubleHints)
        assertEquals(3 + 2, r.words)
    }

    @Test
    fun `example 457 with double hints`() {
        val raw = """
            Strands #457
            “That's proprietary!”
            💡💡🔵💡
            🔵💡🔵💡
            🔵🟡💡💡
            🔵
        """.trimIndent()
        val r = parser.parse(raw, date)!!
        assertEquals(457, r.puzzleId)
        assertEquals("That's proprietary!", r.title)
        // row1: one double, one single; row2: two singles; row3: one double; row4: none
        assertEquals(1 + 2 + 0 + 0, r.hints)
        assertEquals(1 + 0 + 1 + 0, r.doubleHints)
        // words: row1=1, row2=2, row3=1, row4=1
        assertEquals(1 + 2 + 1 + 1, r.words)
    }

    @Test
    fun `invalid header returns null`() {
        assertNull(parser.parse("Strands 462\n💡🔵", date))
    }

    @Test
    fun `too few lines returns null`() {
        assertNull(parser.parse("Strands #500\n“Title”", date))
    }

}