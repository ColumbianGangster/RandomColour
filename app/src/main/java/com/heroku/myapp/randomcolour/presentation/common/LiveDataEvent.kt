package com.heroku.myapp.randomcolour.presentation.common

open class LiveDataEvent<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    /**
     * Sets read state, useful if the ViewModel is shared between several classes
     * Should be combined with LiveData's observer not the LiveDataEventObserver
     */

    fun setHandled(isHandled: Boolean) {
        hasBeenHandled = isHandled
    }
}