package com.heroku.myapp.randomcolour.di

import com.heroku.myapp.randomcolour.data.RandomWordsRepository
import org.koin.dsl.module

val repositoryModules = module {
    single { RandomWordsRepository(
        randomWordApi = get(),
        randomWordDataStore = get()
    ) }
}