package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.api.NetworkResult
import com.example.newsapp.data.entity.NewsDto
import com.example.newsapp.data.repository.util.handleApi
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun dataSource(): NetworkResult<NewsDto> =
        handleApi { apiService.getNews() }
}