package com.ingloriousmind.android.flickrimagesearchdemo

import android.app.Application
import timber.log.Timber

class FlickrImageSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.v("application created")
    }

}
