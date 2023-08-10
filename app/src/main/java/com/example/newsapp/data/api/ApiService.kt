package com.example.newsapp.data.api

import com.example.newsapp.data.entity.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

val BASE_URL = "https://newsapi.org/"

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(@Query("country") country: String = "us"): NetworkResult<NewsDto>

}