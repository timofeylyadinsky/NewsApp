package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.api.NetworkResult
import com.example.newsapp.data.entity.NewsDto
import com.example.newsapp.data.repository.util.NetworkUtil
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun dataSource(): NetworkResult<NewsDto> =
        NetworkUtil().handleApi { apiService.getNews() }
}