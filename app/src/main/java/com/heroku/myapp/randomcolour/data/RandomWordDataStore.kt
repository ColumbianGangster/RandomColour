package com.heroku.myapp.randomcolour.data

import android.content.SharedPreferences
import com.heroku.myapp.randomcolour.BuildConfig

// Follows CRUD as its interface should be indistinguishable from database operations
class RandomWordDataStore(val sharedPreferences: SharedPreferences) {

    fun createRandomWords(randomWords: List<String>) =
        sharedPreferences.edit().putString(BuildConfig.RANDOM_WORDS, randomWords.joinToString()).apply()

    fun readRandomWords() = sharedPreferences.getString(BuildConfig.RANDOM_WORDS, null)?.let {
        it.split(",").map { it.trim() }
    }

    fun updateRandomWords() = readRandomWords()?.let { words ->
        if (words.size >= 2) {
            words.drop(1).let { decrementedWords ->
                createRandomWords(decrementedWords)
                decrementedWords
            }
        } else {
            null
        }
    }
}
