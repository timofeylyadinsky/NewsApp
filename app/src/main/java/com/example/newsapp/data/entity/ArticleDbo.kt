package com.example.newsapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class ArticleDbo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val source: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?
)
