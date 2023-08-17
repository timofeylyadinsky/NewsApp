package com.example.newsapp.domain

import com.example.newsapp.data.api.NetworkResult
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.module.IoDispatcher
import com.example.newsapp.domain.entity.Article
import com.example.newsapp.domain.entity.NewsData
import com.example.newsapp.domain.entity.toArticle
import com.example.newsapp.domain.entity.toNews
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNewsResponseUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<NewsData> = withContext(ioDispatcher) {
        flow {
            when (val response = newsRepository.dataSource()) {
                is NetworkResult.Success -> emit(NewsData(response.data.toNews().articles))
                is NetworkResult.Error -> emit(NewsData(errorMessage = "${response.code} : ${response.message}"))
            }
        }
    }
}

class GetNewsFromDBUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<List<Article>> = withContext(ioDispatcher) {
        newsRepository.getCachedArticles().map {
            it.map { item ->
                item.toArticle()
            }
        }
    }
}