package com.arezoo.offline.data.impl

import android.util.Log
import com.arezoo.offline.data.local.PhotoDao
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.data.remote.APIs
import com.arezoo.offline.domain.repository.PhotoRepository
import io.reactivex.Observable
import javax.inject.Inject

private const val TAG = "PhotoRepositoryImpl"

class PhotoRepositoryImpl @Inject constructor(
    private val service: APIs,
    private val photoDao: PhotoDao
) : PhotoRepository {

    override fun getPhotos(offset: Int, limit: Int): Observable<List<Photo>> {
        val observableFromApi = getPhotosFromRemote(offset = offset, limit = limit)
        val observableFromDb = getPhotosFromDb(offset = offset, limit = limit)
        return Observable.concatArrayEager(observableFromApi, observableFromDb)
    }

    override fun getPhotosFromRemote(offset: Int, limit: Int): Observable<List<Photo>> {
        return service.getPhotos(start = offset, limit = limit)
            .doOnNext {
                Log.d(TAG, "getPhotosFromRemote: list: $it")
                photoDao.insertPhotosToDb(it)
            }
    }

    override fun getPhotosFromDb(offset: Int, limit: Int): Observable<List<Photo>> {
        return photoDao.getPhotos(offset = offset, limit = limit)
            .toObservable()
            .doOnNext {
                Log.d(TAG, "getPhotosFromDb: ${it.size}")
            }
    }
}