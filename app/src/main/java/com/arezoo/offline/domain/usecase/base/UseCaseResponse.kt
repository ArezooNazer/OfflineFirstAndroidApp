package com.arezoo.offline.domain.usecase.base

import com.arezoo.offline.data.remote.ErrorModel


interface UseCaseResponse<T> {

    fun onSuccess(value: T)

    fun onError(errorModel: ErrorModel)
}