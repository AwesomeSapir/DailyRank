package com.sapreme.dailyrank.data.remote

interface InviteRemoteDataSource {

    suspend fun createCode(groupId: String): String
    suspend fun resolve(code: String): String

}