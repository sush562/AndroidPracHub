package com.myprac.advanced.android.interfaces

import android.app.Application
import com.myprac.advanced.android.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(app: Application)
}