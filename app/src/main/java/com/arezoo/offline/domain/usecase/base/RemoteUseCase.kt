package com.arezoo.offline.domain.usecase.base

import com.arezoo.offline.data.remote.ErrorHandler
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

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
            .debounce(300, TimeUnit.MILLISECONDS)//todo: it should be here? to get data from db first
            .subscribe({
                onResponse?.onSuccess(it)
            }, {
                onResponse?.onError(errorHandler.traceErrorException(it))
            }).also {
                compositeDisposable.add(it)
            }
    }
}