package com.heroku.myapp.randomcolour.di

import com.heroku.myapp.randomcolour.presentation.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModels = module {
    viewModel {
        MainViewModel(getInitialRandomWordUseCase = get(),
            getRandomWordManualRefreshUseCase = get(),
            wordToColourMapper = get(named(WORD_TO_COLOUR_MAPPER)),
            uiErrorMapper = get(named(ERROR_UI_MAPPER)))
    }
}