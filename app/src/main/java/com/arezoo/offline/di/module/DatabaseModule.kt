package com.arezoo.offline.di.module

import android.app.Application
import androidx.room.Room
import com.arezoo.offline.data.local.AppDatabase
import com.arezoo.offline.data.local.PhotoDao
import com.arezoo.offline.util.AppConstant.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun providePhotoDao(database: AppDatabase): PhotoDao {
        return database.photoDao()
    }
}