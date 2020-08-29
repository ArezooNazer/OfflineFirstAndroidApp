package com.arezoo.offline.presentation.main

import androidx.lifecycle.MutableLiveData
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.domain.repository.PhotoRepository
import com.arezoo.offline.presentation.base.BaseViewModel
import javax.inject.Inject

private const val TAG = "MainViewModel"


class MainViewModel @Inject constructor(private val repository: PhotoRepository) : BaseViewModel() {

    val photosLiveData = MutableLiveData<List<Photo>>()
    private var isLoadingData = false
    private var isPaginationLastPage = false

    init {
        fetchPhotos(offset = 0)
    }

    fun fetchPhotos(offset: Int) {

    }

    fun getIsLoading(): Boolean {
        return isLoadingData
    }

    fun getIsLastPage(): Boolean {
        return isPaginationLastPage
    }
}