package com.example.newsapp.data.repository.module

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://newsapi.org/"

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideOkHTTPClient() = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val builder = chain.request().url().newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY).build()
            val request = chain.request().newBuilder().url(builder).build()
            return@Interceptor chain.proceed(request)
        })
    }
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

}