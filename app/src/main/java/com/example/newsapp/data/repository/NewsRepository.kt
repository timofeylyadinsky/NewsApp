package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.api.NetworkResult
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.dao.SavedNewsDao
import com.example.newsapp.data.entity.ArticleDto
import com.example.newsapp.data.entity.NewsDto
import com.example.newsapp.data.entity.SavedNewsDbo
import com.example.newsapp.data.entity.toArticleDbo
import com.example.newsapp.data.entity.toArticleDto
import com.example.newsapp.data.repository.util.handleApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
    private val savedNewsDao: SavedNewsDao,
    private val newsDao: NewsDao
) {
    suspend fun dataSource(): NetworkResult<NewsDto> =
        handleApi { apiService.getNews() }

    suspend fun deleteAllArticles() {
        newsDao.deleteAllCachedNews()
    }

    suspend fun insertNewArticlesToDB(articles: List<ArticleDto>) {
        newsDao.insertAllArticles(articles.map { it.toArticleDbo() })
    }

    fun getCachedArticles() =
        newsDao.getAllCachedArticles().map { it.map { item -> item.toArticleDto() } }

    suspend fun saveNews(newsDbo: SavedNewsDbo) {
        savedNewsDao.saveNews(newsDbo)
    }

    fun getSavedNews(url: String) = savedNewsDao.getSavedNews(url)

    suspend fun deleteNews(url: String) {
        savedNewsDao.deleteNews(url)
    }
}