package com.sapreme.dailyrank

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DailyRankApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        /* TODO
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // you can plant a ReleaseTree that forwards to Crashlytics, etc.
            Timber.plant(ReleaseTree())
        }*/
    }
}