package com.arezoo.offline.domain.usecase.base

import com.arezoo.offline.data.remote.ErrorHandler
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class RemoteUseCase<in R, T>(private val errorHandler: ErrorHandler) :
    UseCase<R, Observable<T>>() {

    fun execute(
        compositeDisposable: CompositeDisposable,
        input: R,
        onResponse: UseCaseResponse<T>?
    ): Disposable {
        return this.execute(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onResponse?.onSuccess(it)
            }, {
                onResponse?.onError(errorHandler.traceErrorException(it))
            }).also {
                compositeDisposable.add(it)
            }
    }
}