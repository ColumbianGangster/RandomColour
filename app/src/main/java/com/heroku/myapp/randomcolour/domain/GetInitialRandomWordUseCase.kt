package com.heroku.myapp.randomcolour.domain

import com.heroku.myapp.randomcolour.data.RandomWordsRepository
import com.heroku.myapp.randomcolour.presentation.common.SingleNoParamsUseCase
import com.heroku.myapp.randomcolour.presentation.common.ThreadScheduler
import io.reactivex.Single


class GetInitialRandomWordUseCase(
    threadScheduler: ThreadScheduler,
    private val randomWordsRepository: RandomWordsRepository
) : SingleNoParamsUseCase<List<String>>(threadScheduler) {

    override fun create(): Single<List<String>> =
        randomWordsRepository.getRandomWords(numberOfWords = 5)
}