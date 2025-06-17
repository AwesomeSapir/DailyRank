package com.sapreme.dailyrank

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DailyRankApp: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        val auth = FirebaseAuth.getInstance()

        auth.signInAnonymously()
            .addOnSuccessListener { Timber.i("Signed in anonymously as ${auth.currentUser?.uid}") }
            .addOnFailureListener { e -> Timber.e(e, "Anonymous auth failed") }


        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return "Timber/${super.createStackElementTag(element)}"
            }
        })

        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Timber.e(throwable, "Uncaught exception on thread ${thread.name}")
            defaultHandler?.uncaughtException(thread, throwable)
        }
        /* TODO
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // you can plant a ReleaseTree that forwards to Crashlytics, etc.
            Timber.plant(ReleaseTree())
        }*/
    }
}