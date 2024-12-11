package com.example.avatr.ui

import com.example.avatr.data.AppContainer
import com.example.avatr.data.DefaultAppContainer
import android.app.Application

class AvatrApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}