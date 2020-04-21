package com.heroku.myapp.randomcolour.di

import android.content.Context
import android.content.SharedPreferences
import com.heroku.myapp.randomcolour.data.RandomWordsLocalDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val PREFERENCES_FILE_KEY = "PREFERENCES_FILE_KEY"

val datastoreModules = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
    }

    single { RandomWordsLocalDataStore(get()) }
}