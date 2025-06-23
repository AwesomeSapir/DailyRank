package com.sapreme.dailyrank.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.remote.GameResultRemoteDataSource
import com.sapreme.dailyrank.data.remote.GroupRemoteDataSource
import com.sapreme.dailyrank.data.remote.MembershipRemoteDataSource
import com.sapreme.dailyrank.data.remote.PlayerRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.FirebaseAuthManager
import com.sapreme.dailyrank.data.remote.firebase.FirebaseGameResultRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.FirebaseGroupRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.FirebaseMembershipRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.FirebasePlayerRemoteDataSource
import com.sapreme.dailyrank.data.repository.GameResultRepository
import com.sapreme.dailyrank.data.repository.GroupRepository
import com.sapreme.dailyrank.data.repository.PlayerRepository
import com.sapreme.dailyrank.data.repository.firebase.FirebaseGameResultRepository
import com.sapreme.dailyrank.data.repository.firebase.FirebaseGroupRepository
import com.sapreme.dailyrank.data.repository.firebase.FirebasePlayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providePlayerRemoteDataSource(
        firestore: FirebaseFirestore
    ): PlayerRemoteDataSource = FirebasePlayerRemoteDataSource(firestore)

    @Provides
    fun providePlayerRepository(
        remoteDataSource: PlayerRemoteDataSource,
    ): PlayerRepository {
        return FirebasePlayerRepository(remoteDataSource)
    }

    @Provides
    fun provideGroupRemoteDataSource(
        firestore: FirebaseFirestore
    ): GroupRemoteDataSource = FirebaseGroupRemoteDataSource(firestore)

    @Provides
    fun provideGroupRepository(
        remoteDataSource: GroupRemoteDataSource,
        remoteMembership: MembershipRemoteDataSource
    ): GroupRepository = FirebaseGroupRepository(remoteDataSource, remoteMembership)

    @Provides
    fun provideGameResultRemoteDataSource(
        firestore: FirebaseFirestore
    ): GameResultRemoteDataSource = FirebaseGameResultRemoteDataSource(firestore)

    @Provides
    fun provideGameResultRepository(
        remoteDataSource: GameResultRemoteDataSource,
        authManager: FirebaseAuthManager
    ): GameResultRepository = FirebaseGameResultRepository(remoteDataSource, authManager)

    @Provides
    fun provideMembershipRemoteDataSource(
        firestore: FirebaseFirestore
    ): MembershipRemoteDataSource = FirebaseMembershipRemoteDataSource(firestore)

}