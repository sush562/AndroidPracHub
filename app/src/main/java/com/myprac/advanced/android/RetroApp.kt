package com.myprac.advanced.android

import android.app.Application

class RetroApp : Application() {

    companion object {
        lateinit var instance: RetroApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
