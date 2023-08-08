package com.example.newsapp.data.api

import com.example.newsapp.data.entity.News
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val BASE_URL = "https://newsapi.org/"

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(@Query("apiKey") key: String = "", @Query("country") country: String = "us"): News

}