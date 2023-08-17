package com.example.newsapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.data.entity.SavedNewsDbo

@Dao
interface SavedNewsDao {
    @Query("select * from savedNews where url = :tUrl")
    fun getSavedNews(tUrl: String): SavedNewsDbo?

    @Insert
    suspend fun saveNews(news: SavedNewsDbo)

    @Query("delete from savedNews where url = :tUrl")
    suspend fun deleteNews(tUrl: String)
}