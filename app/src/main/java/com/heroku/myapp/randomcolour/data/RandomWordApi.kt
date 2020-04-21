package com.heroku.myapp.randomcolour.data

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomWordApi {
    @GET("word")
    fun getRandomWords(@Query("number") number: Int): Single<Response<List<String>>>
}