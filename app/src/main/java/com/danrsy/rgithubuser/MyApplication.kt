package com.danrsy.rgithubuser

import android.app.Application
import com.danrsy.rgithubuser.core.di.databaseModule
import com.danrsy.rgithubuser.core.di.networkModule
import com.danrsy.rgithubuser.core.di.repositoryModule
import com.danrsy.rgithubuser.di.useCaseModule
import com.danrsy.rgithubuser.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}