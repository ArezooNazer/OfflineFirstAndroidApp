package com.arezoo.offline.presentation.base

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.arezoo.offline.util.SingleEventLiveData
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    val showProgress = ObservableField<Boolean>()
    val networkError = ObservableField<Boolean>()
    val errorTextMessage = SingleEventLiveData<String>()
    val compositeDisposable = CompositeDisposable()

    init {
        showProgress.set(false)
        networkError.set(false)
    }

    open fun showProgressBar() {
        showProgress.set(true)
    }

    open fun hideProgressBar() {
        showProgress.set(false)

    }

    fun setErrorMessage(message: String) {
        errorTextMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}