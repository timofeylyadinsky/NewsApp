package com.example.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.entity.ArticleDbo

@Database(entities = [ArticleDbo::class], version = 1)
abstract class NewsDB : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}