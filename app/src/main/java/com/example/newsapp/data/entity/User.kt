package com.example.newsapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userInfo")
data class User(
    @PrimaryKey(autoGenerate = false) val uid: Int = 1,
    val isLocked: Boolean,
    val passcode: String
)
