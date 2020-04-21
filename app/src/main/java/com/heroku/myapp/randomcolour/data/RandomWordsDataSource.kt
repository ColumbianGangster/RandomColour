package com.heroku.myapp.randomcolour.data

interface RandomWordsDataSource {
    fun createRandomWords(randomWords: List<String>)
    fun readRandomWords(): List<String>?
    fun updateRandomWords(): List<String>?
}