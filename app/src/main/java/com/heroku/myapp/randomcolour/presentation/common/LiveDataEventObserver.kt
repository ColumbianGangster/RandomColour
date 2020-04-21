package com.heroku.myapp.randomcolour.presentation.common

import androidx.lifecycle.Observer

class LiveDataEventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<LiveDataEvent<T>> {
    override fun onChanged(event: LiveDataEvent<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}