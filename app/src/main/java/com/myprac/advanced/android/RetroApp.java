package com.myprac.advanced.android;

import android.app.Application;

public class RetroApp extends Application {

    private static RetroApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static RetroApp getInstance(){
        return mInstance;
    }
}
