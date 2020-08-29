package com.arezoo.offline.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.domain.repository.PhotoRepository
import com.arezoo.offline.presentation.base.BaseViewModel
import com.arezoo.offline.util.AppConstant.Companion.PAGE_LIMIT
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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
        loadData(offset) { repository.getPhotos(offset, PAGE_LIMIT) }
    }

    fun getIsLoading(): Boolean {
        return isLoadingData
    }

    fun getIsLastPage(): Boolean {
        return isPaginationLastPage
    }


    private fun loadData(offset: Int, fetch: suspend (Int) -> List<Photo>): Job {
        return viewModelScope.launch {
            try {
                showProgressBar()
                val data = fetch(offset)
                Log.d(TAG, "loadData: $data , size= ${data.size}}")
                photosLiveData.value = data
                isLoadingData = false
                isPaginationLastPage = data.size ?: 0 < PAGE_LIMIT
            } catch (e: Exception) {
                Log.d(TAG, "loadData: ${e.message}")
                e.message?.let { setErrorMessage(it) }
                isLoadingData = false
                isPaginationLastPage = false
            } finally {
                hideProgressBar()
            }
        }
    }
}