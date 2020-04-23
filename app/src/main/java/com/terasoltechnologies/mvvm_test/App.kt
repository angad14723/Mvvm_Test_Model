package com.terasoltechnologies.mvvm_test

import android.app.Application
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        /*
        startKoin {
            androidContext(this@App)
            modules(listOf(dbModule, repositoryModule, uiModule))
        }*/
    }
}