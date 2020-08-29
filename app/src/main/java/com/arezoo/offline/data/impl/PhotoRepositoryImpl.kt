package com.arezoo.offline.data.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.arezoo.offline.data.local.PhotoDao
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.data.remote.APIs
import com.arezoo.offline.domain.repository.PhotoRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

private const val TAG = "PhotoRepositoryImpl"

class PhotoRepositoryImpl @Inject constructor(
    private val service: APIs,
    private val dao: PhotoDao
) : PhotoRepository {

    override suspend fun getPhotos(offset: Int, limit: Int): List<Photo> {
        try {
            Log.d(TAG, "getPhotos: from remote")
            getPhotosFromRemote(offset, limit)
        } catch (e: Exception) {
            Log.e(TAG, "getPhotos: ${e.message}")
        } finally {
            val local = dao.getPhotos(offset, limit)
            Log.d(TAG, "getPhotos: from local= ${local.size}")
            return local
        }
    }

    override suspend fun getPhotosFromRemote(offset: Int, limit: Int) {
        val data = service.getPhotos(offset, limit)
        Log.d(TAG, "getPhotosFromRemote: data= ${data.size}")
        dao.insertPhotoToDb(data)
    }
}