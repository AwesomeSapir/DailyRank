package com.sapreme.dailyrank.data.repository.Impl

import com.google.firebase.auth.FirebaseAuth
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.model.GameResultFilter
import com.sapreme.dailyrank.data.parser.GameResultParserFactory
import com.sapreme.dailyrank.data.remote.GameResultRemoteDataSource
import com.sapreme.dailyrank.data.repository.GameResultRepository
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseGameResultRepository @Inject constructor(
    private val remoteDataSource: GameResultRemoteDataSource,
    private val auth: FirebaseAuth
) : GameResultRepository {

    override suspend fun parse(raw: String, date: LocalDate): Result<GameResult> {
        Timber.d("parse called with:\n - raw: $raw\n - date: $date")

        val cleaned = when {
            raw.startsWith("Archive") -> {
                Timber.d("Detected archive header, stripping it")
                raw.substringAfter("\n")
            }

            else -> raw
        }

        val parser = GameResultParserFactory.from(cleaned) ?: return Result.failure(
            IllegalArgumentException("Unrecognized format")
        )
        Timber.i("Found parser: ${parser::class.simpleName}")

        val result = parser.parse(cleaned, date)
            ?: return Result.failure(IllegalArgumentException("Failed to parse game result"))
        Timber.i("Parsing succeeded: $result")

        return Result.success(result)
    }

    override suspend fun submit(gameResult: GameResult) {
        //TODO move to auth handler
        val userId = auth.currentUser?.uid ?: run {
            Timber.e("No authenticated user")
            throw IllegalStateException("User must be signed in before submitting a result")
        }

        remoteDataSource.publishUserGameResult(userId, gameResult)
    }

    override suspend fun getUserResultsBy(
        userId: String,
        filter: GameResultFilter
    ): List<GameResult> {
        return remoteDataSource.getUserGameResultsBy(userId, filter)
    }
}