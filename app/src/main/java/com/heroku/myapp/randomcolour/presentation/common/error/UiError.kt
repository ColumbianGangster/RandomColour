package com.heroku.myapp.randomcolour.presentation.common.error

data class UiError(var title: String? = null,
                   var message: String? = null,
                   var errorViewType: ErrorViewType,
                   var cancelable: Boolean,
                   var throwable: Throwable,
                   var negativeAction: String? = null,
                   var positiveAction: String? = "Retry")


enum class ErrorViewType {
    DIALOG, DIALOG_WITH_NEGATIVE, FULLSCREEN, TOAST, INLINE_ERROR
}