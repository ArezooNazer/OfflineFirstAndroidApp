package com.arezoo.offline.di.provider.repository

import com.arezoo.offline.data.impl.PhotoRepositoryImpl
import com.arezoo.offline.domain.repository.PhotoRepository
import dagger.Binds
import dagger.Module

@Module
interface  RepositoryBuilder {
    @Binds
    fun bindPhotoRepository(repository: PhotoRepositoryImpl): PhotoRepository
}