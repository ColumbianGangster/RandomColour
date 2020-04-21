package com.heroku.myapp.randomcolour.data

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import retrofit2.Response
import io.reactivex.functions.Function


fun <T> throwErrorOrTransform() = SingleTransformer<Response<T>, T> { upstream ->
    upstream.flatMap(handleResponseUnsuccessful())
        .map(mapResponseBody())
}

fun <T> handleResponseUnsuccessful() = Function<Response<T>, SingleSource<Response<T>>> { response ->
    if (!response.isSuccessful) {
        when (response.code()) {
            in 400..499 -> throw NetworkConnectionException(response.message())
            else -> throw Exception(response.message())
        }
    }

    Single.just(response)
}

fun <T> mapResponseBody() = Function<Response<T>, T> { response ->
    response.body()
}