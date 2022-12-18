package com.am.task.di

import com.am.task.ApiConstants
import com.am.task.remote.endpoints.AccountEndpoints
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
class AccountEndpointsModule {
    @Provides
    @Singleton
    fun provideAccountEndpoints(callAdapter: CallAdapter.Factory, jsonConverter: Converter.Factory,  okHttpClient: OkHttpClient): AccountEndpoints {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.AUTH_SERVICE_URL)
            .addConverterFactory(jsonConverter)
            .addCallAdapterFactory(callAdapter)
            .client(okHttpClient)
            .build()
            .create(AccountEndpoints::class.java)
    }
}