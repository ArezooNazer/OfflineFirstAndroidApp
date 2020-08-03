package com.arezoo.offline.di.module

import android.content.Context
import com.arezoo.offline.di.builder.ViewModelFactoryBuilder
import com.arezoo.offline.di.provider.repository.RepositoryBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [ViewModelFactoryBuilder::class, RepositoryBuilder::class]
)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideContext(context: Context): Context = context.applicationContext

}