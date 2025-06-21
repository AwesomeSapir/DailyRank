package com.sapreme.dailyrank.parser

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.remote.firebase.FirebaseAuthManager
import com.sapreme.dailyrank.data.remote.firebase.FirebaseGameResultRemoteDataSource
import com.sapreme.dailyrank.data.repository.firebase.FirebaseGameResultRepository
import com.sapreme.dailyrank.parser.cases.connectionsCases
import com.sapreme.dailyrank.parser.cases.miniCases
import com.sapreme.dailyrank.parser.cases.strandsCases
import com.sapreme.dailyrank.parser.cases.wordleCases
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(JUnitParamsRunner::class)
class GameResultParsingTest {

    private val repo = FirebaseGameResultRepository(
        remote = FirebaseGameResultRemoteDataSource(FirebaseFirestore.getInstance()),
        auth = FirebaseAuthManager(FirebaseAuth.getInstance())
    )
    private val today = LocalDate.now()

    @Test
    @Parameters(method = "cases")
    fun testParse(raw: String, expected: GameResult) = runTest {
        val result = repo.parse(raw, today)
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Suppress("Unused")
    fun cases(): Array<Any> = (wordleCases + connectionsCases + miniCases + strandsCases).map {
        arrayOf(
            it.raw,
            it.expected
        )
    }.toTypedArray()


}