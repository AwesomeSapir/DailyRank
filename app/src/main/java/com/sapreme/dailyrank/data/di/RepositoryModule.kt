package com.sapreme.dailyrank.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.repository.GameResultRepository
import com.sapreme.dailyrank.data.repository.Impl.FirebaseGameResultRepository
import com.sapreme.dailyrank.data.remote.GameResultRemoteDataSource
import com.sapreme.dailyrank.data.remote.Impl.FirebaseGameResultRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideGameResultRemoteDataSource(
        firestore: FirebaseFirestore
    ): GameResultRemoteDataSource = FirebaseGameResultRemoteDataSource(firestore)

    @Provides
    fun provideGameResultRepository(
        remoteDataSource: GameResultRemoteDataSource,
        firebaseAuth: FirebaseAuth
    ): GameResultRepository {
        return FirebaseGameResultRepository(
            remoteDataSource,
            firebaseAuth
        )
    }

}