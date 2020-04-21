package com.heroku.myapp.randomcolour.di

import com.heroku.myapp.randomcolour.domain.Mapper
import com.heroku.myapp.randomcolour.presentation.common.error.ErrorUiMapper
import com.heroku.myapp.randomcolour.presentation.common.error.GenericErrorMapper
import com.heroku.myapp.randomcolour.presentation.common.error.UiError
import com.heroku.myapp.randomcolour.presentation.main.WordsToColourMapper
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {
    // Error
    single { GenericErrorMapper(resources = androidContext().resources) }
    single(named(name = ERROR_UI_MAPPER)) { ErrorUiMapper(genericErrorMapper = get()) as Mapper<Throwable, UiError> }

    // Feature: Word to Colour
    single(named(name = WORD_TO_COLOUR_MAPPER)) { WordsToColourMapper() as Mapper<List<String>, Int> }
}
