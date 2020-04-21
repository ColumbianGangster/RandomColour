package com.heroku.myapp.randomcolour.presentation.main

import android.graphics.ColorSpace
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heroku.myapp.randomcolour.domain.GetInitialRandomWordUseCase
import com.heroku.myapp.randomcolour.domain.GetRandomWordManualRefreshUseCase
import com.heroku.myapp.randomcolour.domain.Mapper
import com.heroku.myapp.randomcolour.presentation.common.BaseViewModel
import com.heroku.myapp.randomcolour.presentation.common.LiveDataEvent
import com.heroku.myapp.randomcolour.presentation.common.error.UiError
import com.heroku.myapp.randomcolour.presentation.common.extensions.addTo


class MainViewModel(
    private val getInitialRandomWordUseCase: GetInitialRandomWordUseCase,
    private val getRandomWordManualRefreshUseCase: GetRandomWordManualRefreshUseCase,
    private val uiErrorMapper: Mapper<Throwable, UiError>,
    private val wordToColourMapper: Mapper<List<String>, Int>
) : BaseViewModel() {

    private val loadingLiveData = MutableLiveData<Boolean>()
    private val contentLiveData = MutableLiveData<LiveDataEvent<State>>()
    private val errorLiveData = MutableLiveData<LiveDataEvent<UiError>>()

    sealed class State {
        data class Content(val word: String?, val colour: Int) : State()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun getInitialColours() {
        getInitialRandomWordUseCase
            .execute()
            .doOnSubscribe { loadingLiveData.value = true }
            .doOnEvent { _, _ -> loadingLiveData.value = false }
            .subscribe(
                {
                    contentLiveData.value = LiveDataEvent(State.Content(
                        word = it.firstOrNull(),
                        colour = wordToColourMapper.map(it)
                    ))
                },
                {
                    errorLiveData.value = LiveDataEvent(uiErrorMapper.map(it))
                }
            )
            .addTo(compositeDisposable)
    }

    fun getManualRefreshColours() {
        getRandomWordManualRefreshUseCase
            .execute()
            .doOnSubscribe { loadingLiveData.value = true }
            .doOnEvent { _, _ -> loadingLiveData.value = false }
            .subscribe(
                {
                    contentLiveData.value = LiveDataEvent(State.Content(
                        word = it.firstOrNull(),
                        colour = wordToColourMapper.map(it)
                    ))
                },
                {
                    errorLiveData.value = LiveDataEvent(uiErrorMapper.map(it))
                }
            )
            .addTo(compositeDisposable)
    }

    fun getLoadingObservable(): MutableLiveData<Boolean> = loadingLiveData
    fun getContentObservable(): LiveData<LiveDataEvent<State>> = contentLiveData
    fun getErrorObservable(): MutableLiveData<LiveDataEvent<UiError>> = errorLiveData
}
