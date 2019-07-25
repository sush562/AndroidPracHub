package com.myprac.advanced.android.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: Application) {

    /*@Provides
    fun provideActivity(): Activity = activity

    @Provides
    @MyApplicationScope
    fun provideSharedPref(): SharedPreferences = activity.getSharedPreferences("practice_pref_file", Context.MODE_PRIVATE)*/

    @Provides @Singleton
    fun provideApplication() = app

}