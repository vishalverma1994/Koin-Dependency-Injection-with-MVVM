package com.koininjection

import android.app.Application
import com.koininjection.di.module.appModule
import com.koininjection.di.module.repoModule
import com.koininjection.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinApplication)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}