package com.example.newsapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savedNews")
data class SavedNewsDbo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String
)