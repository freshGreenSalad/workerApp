package com.tamaki.workerapp

import android.app.Application
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

    companion object{
        private const val ONESIGNAL_APP_ID = "d8f8fee0-faee-4efd-9125-5070286b5c82"
    }
}