package com.heroku.myapp.randomcolour.data

import io.reactivex.Single
import java.net.UnknownHostException


class RandomWordsRepository(
    val randomWordApi: RandomWordApi,
    val randomWordDataSource: RandomWordsDataSource
) {

    fun getRandomWords(numberOfWords: Int) = randomWordDataSource.readRandomWords()?.let {
        Single.just(it)
    } ?: getRandomWordsManualRefresh(numberOfWords)

    fun getRandomWordsManualRefresh(numberOfWords: Int) =
        randomWordApi.getRandomWords(numberOfWords)
            .compose(throwErrorOrTransform())
            .map { response -> randomWordDataSource.createRandomWords(response).let { response } }
            .onErrorResumeNext {
                if (it is NetworkConnectionException || it is UnknownHostException) {
                    randomWordDataSource.updateRandomWords()?.let { Single.just(it) }
                        ?: Single.error(it)
                } else {
                    Single.error(it)
                }
            }
}
