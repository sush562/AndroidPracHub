package com.myprac.advanced.android.activity

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.myprac.advanced.android.R
import javax.inject.Inject

class DaggerTutorialActivity : AppCompatActivity() {

    @Inject
    lateinit var app : Application

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger_tutorial)

        Log.e("Test", app.getString(R.string.home_page))


    }
}
