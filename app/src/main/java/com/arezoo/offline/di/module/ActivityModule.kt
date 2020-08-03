package com.arezoo.offline.di.module

import com.arezoo.offline.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {

    @ContributesAndroidInjector(
        modules = [
        ]
    )
    fun mainActivityInjector(): MainActivity
}