package com.example.newsapp.domain

import android.util.Log
import com.example.newsapp.data.entity.Article
import com.example.newsapp.data.entity.News
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.module.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<Article> = withContext(ioDispatcher) {
        var list = listOf<Article>()
        val call = newsRepository.getNewsList()
        call.enqueue(object : Callback<News> {

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("!!!", t.toString())
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                list = response.body()!!.articles
            }

        })
        return@withContext list
    }
}