package com.sapreme.dailyrank.data.remote.firebase.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class MemberDto(
    @DocumentId var uid: String = "",
    var joinedAt: Timestamp = Timestamp.now()
)
