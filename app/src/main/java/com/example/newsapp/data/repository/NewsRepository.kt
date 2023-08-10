package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.api.NetworkResult
import com.example.newsapp.data.entity.NewsDto
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun<T: Any> handleApi(
        execute: suspend () -> Response<T>
    ) : NetworkResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode(), e.message)
        }
    }

    suspend fun dataSource() :NetworkResult<NewsDto> = handleApi { apiService.getNews() }
}