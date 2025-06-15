package com.sapreme.dailyrank.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.repository.GameResultRepository
import com.sapreme.dailyrank.data.repository.Impl.FirebaseGameResultRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideGameResultRepository(): GameResultRepository {
        return FirebaseGameResultRepository(
            firestore = FirebaseFirestore.getInstance(),
            auth = FirebaseAuth.getInstance()
        )
    }

}