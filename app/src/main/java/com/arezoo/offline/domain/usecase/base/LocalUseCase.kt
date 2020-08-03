package com.arezoo.offline.domain.usecase.base

import com.arezoo.offline.data.remote.ErrorHandler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class LocalUseCase<in R, T>(private val errorHandler: ErrorHandler) :
    UseCase<R,  Single<T>>() {

    fun execute(
        compositeDisposable: CompositeDisposable,
        input: R,
        onResponse: UseCaseResponse<T>?
    ): Disposable {
        return Single.fromCallable {
            execute(input)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onResponse?.onSuccess(it as T )
            }, {
                onResponse?.onError(errorHandler.traceErrorException(it))
            }).also {
                compositeDisposable.add(it)
            }
    }

}