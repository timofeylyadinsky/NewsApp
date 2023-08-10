package com.example.newsapp.domain

import com.example.newsapp.data.api.NetworkResult
import com.example.newsapp.data.entity.ArticleDto
import com.example.newsapp.data.entity.NewsDto
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.module.IoDispatcher
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

