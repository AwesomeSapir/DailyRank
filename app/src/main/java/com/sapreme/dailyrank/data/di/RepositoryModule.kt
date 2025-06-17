package com.sapreme.dailyrank.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.repository.GameResultRepository
import com.sapreme.dailyrank.data.repository.firebase.FirebaseGameResultRepository
import com.sapreme.dailyrank.data.remote.GameResultRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.FirebaseGroupRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.FirebaseGameResultRemoteDataSource
import com.sapreme.dailyrank.data.repository.GroupRepository
import com.sapreme.dailyrank.data.repository.firebase.FirebaseGroupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideGroupRemoteDataSource(
        firestore: FirebaseFirestore
    ): FirebaseGroupRemoteDataSource = FirebaseGroupRemoteDataSource(firestore)

    @Provides
    fun provideGroupRepository(
        remoteDataSource: FirebaseGroupRemoteDataSource,
    ): GroupRepository {
        return FirebaseGroupRepository(remoteDataSource)
    }

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