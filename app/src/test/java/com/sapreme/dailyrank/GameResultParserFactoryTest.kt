package com.sapreme.dailyrank

import com.sapreme.dailyrank.data.parser.GameResultParserFactory
import com.sapreme.dailyrank.data.parser.impl.ConnectionsResultParser
import com.sapreme.dailyrank.data.parser.impl.MiniResultParser
import com.sapreme.dailyrank.data.parser.impl.StrandsResultParser
import com.sapreme.dailyrank.data.parser.impl.WordleResultParser
import kotlin.test.*

class GameResultParserFactoryTest {

    @Test
    fun `from returns WordleResultParser when raw starts with Wordle`() {
        val raw = "Wordle 123 4/6\n🟩🟩🟩🟩"
        val parser = GameResultParserFactory.from(raw)
        assertNotNull(parser)
        assertTrue(parser is WordleResultParser)
    }

    @Test
    fun `from returns ConnectionsResultParser when raw starts with Connections`() {
        val raw = "Connections\nPuzzle #1\n🟨🟨🟨🟨"
        val parser = GameResultParserFactory.from(raw)
        assertNotNull(parser)
        assertTrue(parser is ConnectionsResultParser)
    }

    @Test
    fun `from returns StrandsResultParser when raw starts with Strands`() {
        val raw = "Strands #10\n“Title”\n💡🔵"
        val parser = GameResultParserFactory.from(raw)
        assertNotNull(parser)
        assertTrue(parser is StrandsResultParser)
    }

    @Test
    fun `from returns MiniResultParser when raw contains New York Times Mini Crossword`() {
        val raw = "I solved the 6/8/2025 New York Times Mini Crossword in 1:39!"
        val parser = GameResultParserFactory.from(raw)
        assertNotNull(parser)
        assertTrue(parser is MiniResultParser)
    }

    @Test
    fun `from returns null for unknown formats`() {
        val raw = "Some other game result text"
        val parser = GameResultParserFactory.from(raw)
        assertNull(parser)
    }

}