package com.ingloriousmind.android.flickrimagesearchdemo

import android.app.Application
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.cache.LruResourceCache
import timber.log.Timber

/**
 * the app.
 */
class FlickrImageSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        GlideBuilder().setMemoryCache(LruResourceCache(10 * 1000 * 1000)).build(this)

        Timber.v("it's on!")
    }

}
