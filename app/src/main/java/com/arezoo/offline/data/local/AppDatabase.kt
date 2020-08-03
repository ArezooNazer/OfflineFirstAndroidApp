package com.arezoo.offline.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arezoo.offline.data.model.Photo

@Database(entities = [Photo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

}