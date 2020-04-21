package com.heroku.myapp.randomcolour.presentation.common

import io.reactivex.Single

/**
 * This class serves as an abstract use case to create or transform a Single stream
 * @param T - A generic object of type T. For example - SingleNoParamsUseCase<String> would lead
 * to creation of Single<String> once create() is called
 */
abstract class SingleNoParamsUseCase<T>(private val threadScheduler: ThreadScheduler) {

    abstract fun create(): Single<T>

    fun execute(): Single<T> = create()
            .subscribeOn(threadScheduler.io())
            .observeOn(threadScheduler.main())
}