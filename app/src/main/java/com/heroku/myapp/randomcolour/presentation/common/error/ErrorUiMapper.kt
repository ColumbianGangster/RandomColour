package com.heroku.myapp.randomcolour.presentation.common.error

import com.heroku.myapp.randomcolour.domain.Mapper


class ErrorUiMapper(val genericErrorMapper: GenericErrorMapper) : Mapper<Throwable, UiError> {
    override fun map(from: Throwable) = UiError(
            title = genericErrorMapper.transformToTitle(from),
            message = genericErrorMapper.transformToMessage(from),
            errorViewType = ErrorViewType.DIALOG,
            positiveAction = genericErrorMapper.transformToPositiveAction(from),
            negativeAction = genericErrorMapper.transformToNegativeAction(from),
            cancelable = true,
            throwable = from
    )
}