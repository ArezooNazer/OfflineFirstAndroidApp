package com.arezoo.offline.di.builder

import androidx.lifecycle.ViewModel
import com.arezoo.offline.di.ViewModelKey
import com.arezoo.offline.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}