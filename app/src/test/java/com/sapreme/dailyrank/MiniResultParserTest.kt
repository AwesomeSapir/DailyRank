package com.sapreme.dailyrank

import com.sapreme.dailyrank.data.parser.impl.MiniResultParser
import com.sapreme.dailyrank.data.parser.impl.WordleResultParser
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class MiniResultParserTest {

    private val parser = MiniResultParser()
    private val submitted = LocalDate.of(2025, 6, 9)

    @Test
    fun `parse example 6_8_2025 in 1 minute 39 seconds`() {
        val raw = "I solved the 6/8/2025 New York Times Mini Crossword in 1:39!"
        val result = parser.parse(raw, submitted)
        assertNotNull(result)
        with(result) {
            assertEquals(20250608, puzzleId)
            assertEquals(submitted, date)
            assertTrue(succeeded)
            assertEquals(1.minutes+39.seconds, duration)
        }
    }

    @Test
    fun `parse example 5_31_2025 in 3 minutes 24 seconds`() {
        val raw = "I solved the 5/31/2025 New York Times Mini Crossword in 3:24!"
        val result = parser.parse(raw, submitted)
        assertNotNull(result)
        with(result) {
            assertEquals(20250531, puzzleId)
            assertEquals(submitted, date)
            assertTrue(succeeded)
            assertEquals(3.minutes + 24.seconds, duration)
        }
    }

    @Test
    fun `parse example 5_9_2025 in 0 minutes 50 seconds`() {
        val raw = "I solved the 5/9/2025 New York Times Mini Crossword in 0:50!"
        val result = parser.parse(raw, submitted)
        assertNotNull(result)
        with(result) {
            assertEquals(20250509, puzzleId)
            assertEquals(submitted, date)
            assertTrue(succeeded)
            assertEquals(50.seconds, duration)
        }
    }

    @Test
    fun `parse example 12_1_2024 in 1 minute 2 seconds`() {
        val raw = "I solved the 12/1/2024 New York Times Mini Crossword in 1:02!"
        val result = parser.parse(raw, submitted)
        assertNotNull(result)
        with(result) {
            assertEquals(20241201, puzzleId)
            assertEquals(submitted, date)
            assertTrue(succeeded)
            assertEquals(1.minutes + 2.seconds, duration)
        }
    }

    @Test
    fun `parse with leading spaces still works`() {
        val raw = "   I solved the 1/1/2025 New York Times Mini Crossword in 0:07!  "
        val result = parser.parse(raw, submitted)
        assertNotNull(result)
        with(result) {
            assertEquals(20250101, puzzleId)
            assertEquals(submitted, date)
            assertTrue(succeeded)
            assertEquals(7.seconds, duration)
        }
    }

    @Test
    fun `invalid format returns null`() {
        val raw = "Solved the Mini on 6/8/2025 in 1:39!"
        assertNull(parser.parse(raw, submitted))
    }

}