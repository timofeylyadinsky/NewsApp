package com.example.newsapp.data.repository.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

const val BASE_URL = "https://newsapi.org/"

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providesBaseUrl(): String = BASE_URL

}