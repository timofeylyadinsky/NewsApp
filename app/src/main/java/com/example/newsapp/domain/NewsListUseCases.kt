package com.example.newsapp.domain

import com.example.newsapp.data.api.NetworkResult
import com.example.newsapp.data.entity.NewsDto
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.module.IoDispatcher
import com.example.newsapp.domain.entity.News
import com.example.newsapp.domain.entity.toNews
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNewsResponseUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): NetworkResult<NewsDto> = withContext(ioDispatcher) {
        newsRepository.dataSource()
    }
}

class MapArticleListUseCase @Inject constructor(
    private val getNewsResponseUseCase: GetNewsResponseUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(ioDispatcher) {
        when (val response = getNewsResponseUseCase.invoke()) {
            is NetworkResult.Success -> NetworkResult.Success(response.data.toNews())
            is NetworkResult.Error -> NetworkResult.Error(
                code = response.code,
                message = response.message
            )
        }
    }
}