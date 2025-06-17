package com.sapreme.dailyrank.ui.util

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneOffset

fun LocalDate.toFirestoreTimestamp(): Timestamp {
    val instant = this.atStartOfDay().toInstant(ZoneOffset.UTC)
    return Timestamp(instant.epochSecond, 0)
}

fun Timestamp.toLocalDate(): LocalDate {
    return this.toDate().toInstant()
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
}