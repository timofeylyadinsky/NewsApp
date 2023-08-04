package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getNewsList() = apiService.getNews("")
}