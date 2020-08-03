package com.arezoo.offline.di.component

import android.app.Application
import com.arezoo.offline.di.module.ActivityModule
import com.arezoo.offline.di.module.ApplicationModule
import com.arezoo.offline.di.module.NetworkModule
import com.arezoo.offline.presentation.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance app: Application
        ): AppComponent
    }

    fun inject(application: MainApplication)
}