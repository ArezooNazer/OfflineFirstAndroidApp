package com.arezoo.offline.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.arezoo.offline.data.model.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = REPLACE)
    fun insertPhotoToDb(photo: Photo)

    @Query("SELECT * FROM PHOTO")
    fun getPhotos(): List<Photo>
}