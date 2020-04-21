package com.heroku.myapp.randomcolour.di

import com.heroku.myapp.randomcolour.BuildConfig
import com.heroku.myapp.randomcolour.data.RandomWordApi
import com.heroku.myapp.randomcolour.presentation.common.RxScheduler
import com.heroku.myapp.randomcolour.presentation.common.ThreadScheduler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModules = module {
    single(named(name = RETROFIT_INSTANCE_RANDOM_WORD_API)) { createNetworkClient(BuildConfig.RANDOM_WORD_API) }
    single { (get(named(RETROFIT_INSTANCE_RANDOM_WORD_API)) as Retrofit).create(RandomWordApi::class.java) }
    factory<ThreadScheduler> { RxScheduler() }
}

fun createNetworkClient(baseUrl: String) =
    retrofitClient(baseUrl, httpClient())

private fun httpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    clientBuilder.readTimeout(120, TimeUnit.SECONDS)
    clientBuilder.writeTimeout(120, TimeUnit.SECONDS)
    return clientBuilder.build()
}

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()