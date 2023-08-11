package com.example.newsapp.domain

import com.example.newsapp.data.api.NetworkResult
import com.example.newsapp.data.entity.NewsDto
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.module.IoDispatcher
import com.example.newsapp.domain.entity.News
import com.example.newsapp.domain.entity.NewsData
import com.example.newsapp.domain.entity.toNews
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNewsResponseUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): NetworkResult<News> = withContext(ioDispatcher) {
        when (val response = newsRepository.dataSource()) {
            is NetworkResult.Success -> NetworkResult.Success(data = response.data.toNews())
            is NetworkResult.Error -> NetworkResult.Error(
                code = response.code,
                message = response.message
            )
        }
    }
}

class MapArticleListUseCase @Inject constructor(
    private val getNewsResponseUseCase: GetNewsResponseUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<NewsData> = withContext(ioDispatcher) {
        flow {
            when (val response = getNewsResponseUseCase.invoke()) {
                is NetworkResult.Success -> emit(NewsData(response.data.articles))
                is NetworkResult.Error -> emit(NewsData(errorMessage = "${response.code} : ${response.message}"))
            }
        }
    }
}