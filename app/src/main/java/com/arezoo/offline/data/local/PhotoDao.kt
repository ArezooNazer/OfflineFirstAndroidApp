package com.arezoo.offline.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.arezoo.offline.data.model.Photo
import io.reactivex.Single

@Dao
interface PhotoDao {

    @Insert(onConflict = REPLACE)
    fun insertPhotosToDb(photos: List<Photo>)

    @Query("SELECT * FROM PHOTO LIMIT :limit OFFSET :offset")
    fun getPhotos(offset: Int, limit: Int): Single<List<Photo>>
}