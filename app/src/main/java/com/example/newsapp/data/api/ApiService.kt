package com.example.newsapp.data.api

import com.example.newsapp.data.entity.Article
import retrofit2.Call
import retrofit2.http.GET

val BASE_URL = "https://newsapi.org/"

interface ApiService {
    @GET("v2/top-headlines")
    fun getArticle() : Call<Article>

}