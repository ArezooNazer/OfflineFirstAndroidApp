package com.arezoo.offline.presentation.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.data.remote.ErrorHandler
import com.arezoo.offline.data.remote.ErrorModel
import com.arezoo.offline.domain.usecase.GetPhotosUseCase
import com.arezoo.offline.domain.usecase.base.UseCaseResponse
import com.arezoo.offline.presentation.base.BaseViewModel
import com.arezoo.offline.util.AppConstant.Companion.PAGE_LIMIT
import javax.inject.Inject

private const val TAG = "MainViewModel"


class MainViewModel @Inject constructor(
    private val useCase: GetPhotosUseCase
) : BaseViewModel() {

    val photosLiveData = MutableLiveData<List<Photo>>()
    private var isLoadingData = false
    private var isPaginationLastPage = false

    init {
        fetchPhotos(offset = 0)
    }

    fun fetchPhotos(offset: Int) {
        Log.d(TAG, "fetchPhotos: ")
        showProgressBar()
        useCase.execute(compositeDisposable, offset, object : UseCaseResponse<List<Photo>> {
            override fun onSuccess(value: List<Photo>) {
                hideProgressBar()
                photosLiveData.value = value
                isLoadingData = false
                isPaginationLastPage = value.size < PAGE_LIMIT
            }

            override fun onError(errorModel: ErrorModel) {
                hideProgressBar()
                setErrorMessage(errorModel.message ?: "")
                isLoadingData = false
                isPaginationLastPage = true
            }

        })
    }

    fun getIsLoading(): Boolean {
        return isLoadingData
    }

    fun getIsLastPage(): Boolean {
        return isPaginationLastPage
    }
}