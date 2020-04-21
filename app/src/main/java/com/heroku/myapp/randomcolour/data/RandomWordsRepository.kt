package com.heroku.myapp.randomcolour.data

import io.reactivex.Single
import java.net.UnknownHostException


class RandomWordsRepository(
    val randomWordApi: RandomWordApi,
    val randomWordDataStore: RandomWordDataStore
) {

    fun getRandomWords(numberOfWords: Int) = randomWordDataStore.readRandomWords()?.let {
        Single.just(it)
    } ?: getRandomWordsManualRefresh(numberOfWords)

    fun getRandomWordsManualRefresh(numberOfWords: Int) =
        randomWordApi.getRandomWords(numberOfWords)
            .compose(throwErrorOrTransform())
            .map { response -> randomWordDataStore.createRandomWords(response).let { response } }
            .onErrorResumeNext {
                if (it is NetworkConnectionException || it is UnknownHostException) {
                    randomWordDataStore.updateRandomWords()?.let { Single.just(it) }
                        ?: Single.error(it)
                } else {
                    Single.error(it)
                }
            }
}
