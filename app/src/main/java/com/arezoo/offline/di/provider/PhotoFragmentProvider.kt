package com.arezoo.offline.di.provider

import com.arezoo.offline.presentation.photo.PhotoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface PhotoFragmentProvider {

    @ContributesAndroidInjector
    fun providePhotoFragment(): PhotoFragment
}