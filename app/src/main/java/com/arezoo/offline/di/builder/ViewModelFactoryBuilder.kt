package com.arezoo.offline.di.builder

import androidx.lifecycle.ViewModelProvider
import com.arezoo.offline.presentation.base.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [(ViewModelBuilder::class)])
interface ViewModelFactoryBuilder {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}