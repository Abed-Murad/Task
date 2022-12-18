package com.am.task.di

import com.am.task.ApiConstants
import com.am.task.remote.endpoints.PixabayEndpoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class PixabayEndpointsModule {
    @Provides
    @Singleton
    fun providePixabayEndpoints(callAdapter: CallAdapter.Factory, jsonConverter: Converter.Factory,  okHttpClient: OkHttpClient): PixabayEndpoints {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.PIXABAY_URL)
            .addConverterFactory(jsonConverter)
            .addCallAdapterFactory(callAdapter)
            .client(okHttpClient)
            .build()
            .create(PixabayEndpoints::class.java)
    }
}