package com.arezoo.offline.data.impl

import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.data.remote.APIs
import com.arezoo.offline.domain.repository.PhotoRepository
import io.reactivex.Observable
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val service: APIs) : PhotoRepository {

    override fun getPhotosFromRemote(offset: Int, limit: Int): Observable<List<Photo>> {
//        return Observable.fromCallable { service.getPhotos(start = offset, limit = limit) }
    }
}