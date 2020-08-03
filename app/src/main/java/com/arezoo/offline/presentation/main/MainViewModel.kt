package com.arezoo.offline.presentation.main

import androidx.lifecycle.MutableLiveData
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.data.remote.ErrorHandler
import com.arezoo.offline.presentation.base.BaseViewModel
import javax.inject.Inject

private const val TAG = "MainViewModel"

class MainViewModel @Inject constructor(
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    val photosLiveData = MutableLiveData<List<Photo>>()
}