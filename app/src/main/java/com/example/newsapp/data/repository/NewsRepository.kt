package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.api.NetworkResult
import com.example.newsapp.data.dao.SavedNewsDao
import com.example.newsapp.data.entity.NewsDto
import com.example.newsapp.data.entity.SavedNewsDbo
import com.example.newsapp.data.repository.util.handleApi
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
    private val newsDao: SavedNewsDao
) {
    suspend fun dataSource(): NetworkResult<NewsDto> =
        handleApi { apiService.getNews() }

    suspend fun saveNews(newsDbo: SavedNewsDbo) {
        newsDao.saveNews(newsDbo)
    }

    fun getSavedNews(url: String) = newsDao.getSavedNews(url)

    suspend fun deleteNews(url: String) {
        newsDao.deleteNews(url)
    }
}