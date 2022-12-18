package com.am.task.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.am.task.remote.network.ApiThrowableInterceptor
import com.am.task.remote.network.LiveDataCallAdapterFactory
import com.am.task.validation.Validator
import com.am.task.validation.ValidatorImpl
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.*
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideJacksonConverterFactory(mapper: ObjectMapper): Converter.Factory {
        return JacksonConverterFactory.create(mapper)
    }

    @Singleton
    @Provides
    fun provideJacksonKotlinMapper(): ObjectMapper {
        return JsonMapper.builder().serializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build().registerKotlinModule()
    }

    @Singleton
    @Provides
    fun provideCallToLiveDataAdapterFactory(interceptor: ApiThrowableInterceptor): CallAdapter.Factory {
        return LiveDataCallAdapterFactory(interceptor)
    }


    @Singleton
    @Provides
    fun provideFormValidator(): Validator {
        return ValidatorImpl()
    }

    @Singleton
    @Provides
    fun provideResources(context: Context): Resources {
        return context.resources
    }


    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        return builder.build()
    }
}
