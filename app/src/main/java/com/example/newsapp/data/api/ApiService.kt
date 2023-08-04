package com.example.newsapp.data.api

import com.example.newsapp.R
import com.example.newsapp.data.entity.News
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

val BASE_URL = "https://newsapi.org/"

interface ApiService {
    @GET("v2/top-headlines")
    fun getNews(@Path("apiKey") key: String, @Path("country") country: String ="us"): Call<News>

    companion object Factory {
        fun create(): ApiService {
            val okHttpClient = OkHttpClient.Builder()
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}