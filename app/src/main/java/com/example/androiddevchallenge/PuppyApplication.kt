package com.example.androiddevchallenge

import android.app.Application

class PuppyApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()
    }
}