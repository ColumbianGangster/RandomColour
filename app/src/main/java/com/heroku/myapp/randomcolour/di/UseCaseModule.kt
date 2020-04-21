package com.heroku.myapp.randomcolour.di

import com.heroku.myapp.randomcolour.domain.GetInitialRandomWordUseCase
import com.heroku.myapp.randomcolour.domain.GetRandomWordManualRefreshUseCase
import org.koin.dsl.module

val useCaseModules = module {

    factory {
        GetInitialRandomWordUseCase(
            threadScheduler = get(),
            randomWordsRepository = get()
        )
    }

    factory {
        GetRandomWordManualRefreshUseCase(
            threadScheduler = get(),
            randomWordsRepository = get()
        )
    }
}