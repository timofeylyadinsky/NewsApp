package com.example.newsapp.domain

import com.example.newsapp.data.entity.ArticleDto
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.module.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<ArticleDto> = withContext(ioDispatcher) {
        newsRepository.getNewsList().articles
    }
}