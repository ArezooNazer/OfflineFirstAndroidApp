package com.arezoo.offline.data.remote

import com.arezoo.offline.data.model.Photo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface APIs {

    @GET("photos")
    fun getPhotos(@Query("_start") start: Int,
                  @Query("_limit") limit: Int
    ): Observable<List<Photo>>
}
