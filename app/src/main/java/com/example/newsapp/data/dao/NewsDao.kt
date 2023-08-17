package com.example.newsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.data.entity.ArticleDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("delete from news")
    suspend fun deleteAllCachedNews()

    @Insert
    suspend fun insertAllArticles(articlesDbo: List<ArticleDbo>)

    @Query("select * from news")
    fun getAllCachedArticles(): Flow<List<ArticleDbo>>
}