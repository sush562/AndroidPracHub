package com.myprac.advanced.android

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Utils {

    fun isNetworkAvailable() : Boolean{
        val connectivityManager: ConnectivityManager = RetroApp.instance.
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected()
    }
}