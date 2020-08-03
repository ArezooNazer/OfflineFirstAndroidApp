package com.arezoo.offline.domain.repository

import com.arezoo.offline.data.model.Photo
import io.reactivex.Observable

interface PhotoRepository {
    fun getPhotosFromRemote(offset: Int, limit: Int): Observable<List<Photo>>
}