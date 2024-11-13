package com.example.labb_3_android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp



@HiltAndroidApp
class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
