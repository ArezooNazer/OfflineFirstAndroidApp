package com.arezoo.offline.domain.usecase

import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.data.remote.ErrorHandler
import com.arezoo.offline.domain.repository.PhotoRepository
import com.arezoo.offline.domain.usecase.base.RemoteUseCase
import com.arezoo.offline.util.AppConstant.Companion.PAGE_LIMIT
import io.reactivex.Observable
import javax.inject.Inject


class GetPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository,
    handler: ErrorHandler
) : RemoteUseCase<Int, List<Photo>>(handler) {

    override fun execute(input: Int): Observable<List<Photo>> {
        return repository.getPhotos(offset = input, limit = PAGE_LIMIT)
    }
}