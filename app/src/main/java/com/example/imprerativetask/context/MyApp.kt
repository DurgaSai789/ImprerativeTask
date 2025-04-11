package com.example.imprerativetask.context

import android.app.Application
import com.example.imprerativetask.secureStorage.SecureStorage

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SecureStorage.init(this)
    }
}