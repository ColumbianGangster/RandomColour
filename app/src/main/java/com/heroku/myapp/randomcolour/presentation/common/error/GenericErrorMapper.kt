package com.heroku.myapp.randomcolour.presentation.common.error

import android.content.res.Resources
import com.heroku.myapp.randomcolour.R
import com.heroku.myapp.randomcolour.data.NetworkConnectionException


class GenericErrorMapper(val resources: Resources) {

    fun transformToTitle(throwable: Throwable): String =
            when (throwable) {
                is NetworkConnectionException -> networkConnectionTitle(resources)
                else -> "Error"
            }

    fun transformToMessage(throwable: Throwable): String =
            when (throwable) {
                is NetworkConnectionException -> networkConnectionMessage(resources)
                else -> somethingWentWrongMessage(resources)
            }

    fun transformToNegativeAction(from: Throwable): String? = when(from) {
        is NetworkConnectionException -> ""
        else -> "Cancel"
    }

    fun transformToPositiveAction(from: Throwable): String?  = when (from){
        is NetworkConnectionException -> "Retry"
        else -> ""
    }
}

// Generic
fun somethingWentWrongMessage(resources: Resources) = resources.getString(R.string.error_generic_message)
fun networkConnectionTitle(resources: Resources) = resources.getString(R.string.error_no_network_message)
fun networkConnectionMessage(resources: Resources) = resources.getString(R.string.error_no_network_title)