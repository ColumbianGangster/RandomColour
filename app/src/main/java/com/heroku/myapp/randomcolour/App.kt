package com.heroku.myapp.randomcolour

import android.app.Application
import com.heroku.myapp.randomcolour.di.*
import io.reactivex.internal.functions.Functions
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        loadKoin()
        RxJavaPlugins.setErrorHandler(Functions.emptyConsumer())
    }

    private fun loadKoin() {
        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    networkModules,
                    datastoreModules,
                    repositoryModules,
                    useCaseModules,
                    mapperModule,
                    viewModels
                )
            )
        }
    }
}