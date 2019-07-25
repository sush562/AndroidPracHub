package com.myprac.advanced.android

import android.app.Application
import com.myprac.advanced.android.interfaces.ApplicationComponent
import com.myprac.advanced.android.interfaces.DaggerApplicationComponent
import com.myprac.advanced.android.module.ApplicationModule

class RetroApp : Application() {

    companion object {
        lateinit var instance: RetroApp
            private set
        var imageBasePath: String = "https://image.tmdb.org/t/p/"
    }

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component.inject(this)
    }
}
