package com.arezoo.offline.di.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import com.arezoo.offline.BuildConfig
import com.arezoo.offline.data.remote.APIs
import com.arezoo.offline.data.remote.ErrorHandler
import com.arezoo.offline.util.AppConstant
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ApplicationModule::class])
class NetworkModule {
    private val TAG = "NetworkModule"
    private val TIME_OUT = 60L

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstant.API_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(application: Application): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
            cache(provideCache(application))
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .headers(getJsonHeader())
                    .cacheControl(getCacheControl(application))
                    .build()
                chain.proceed(request)
            }
            addNetworkInterceptor(interceptor)
        }

        return client.build()
    }

    private fun getCacheControl(application: Application): CacheControl {
        return CacheControl.Builder().apply {
            if (provideIsNetworkAvailable(application))
                maxAge(1, TimeUnit.MINUTES)
            else {
                onlyIfCached()
                maxStale(24, TimeUnit.HOURS)
            }
        }.build()
    }

    private fun getJsonHeader(): Headers {
        return Headers.Builder().apply {
            add("Content-Type", "application/json")
            add("Accept", "application/json")
//            add(
//                "User-Agent",
//                "${BuildConfig.VERSION_NAME};${Build.MANUFACTURER};${Build.MODEL};Android-${Build.VERSION.RELEASE}"
//            )
        }.build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    /**
     * NetworkCapabilities is not deprecated in API 29 but it requires API 21 so I have called it on API 29 only.
     * However getActiveNetworkInfo() is deprecated only in API 29 and works on all APIs , so we can use it in all apis bellow 29
     */

    @Provides
    @Singleton
    fun provideIsNetworkAvailable(application: Application): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                return when {
                    hasTransport(TRANSPORT_CELLULAR) -> true
                    hasTransport(TRANSPORT_WIFI) -> true
                    hasTransport(TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
            return false
        }

        try {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                Log.d(TAG, "provideIsNetworkAvailable: network is available")
                return true
            }
        } catch (e: Exception) {
            Log.d(TAG, "provideIsNetworkAvailable() error = $e")
            return false
        }
        return false
    }

    @Provides
    fun provideCache(application: Application): Cache? {
        val cacheSize = (10 * 1024 * 1024).toLong()
        var cache: Cache? = null
        try {
            cache = Cache(File(application.cacheDir, "http-cache"), cacheSize)
        } catch (e: java.lang.Exception) {
            Log.e("Cache", "Error in creating  Cache!")
        }

        return cache
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): APIs {
        return retrofit.create(APIs::class.java)
    }

    @Provides
    @Singleton
    fun providesErrorHandler(): ErrorHandler {
        return ErrorHandler()
    }
}