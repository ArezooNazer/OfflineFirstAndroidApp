package com.arezoo.offline.domain.repository

import com.arezoo.offline.data.model.Photo
import io.reactivex.Observable

interface PhotoRepository {
    fun getPhotos(offset: Int, limit: Int): Observable<List<Photo>>

    fun getPhotosFromRemote(offset: Int, limit: Int): Observable<List<Photo>>

    fun getPhotosFromDb(offset: Int, limit: Int): Observable<List<Photo>>
}