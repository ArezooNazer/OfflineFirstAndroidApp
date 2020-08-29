package com.arezoo.offline.domain.repository

import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.util.AppConstant.Companion.PAGE_LIMIT

interface PhotoRepository {
    suspend fun getPhotos(offset: Int, limit: Int = PAGE_LIMIT): List<Photo>

    suspend fun getPhotosFromRemote(offset: Int, limit: Int = PAGE_LIMIT)
}