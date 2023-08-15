package com.example.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.dao.SavedNewsDao
import com.example.newsapp.data.entity.SavedNewsDbo

@Database(entities = [SavedNewsDbo::class], version = 1)
abstract class SavedNewsDB : RoomDatabase() {
    abstract fun savedNewsDao(): SavedNewsDao
}