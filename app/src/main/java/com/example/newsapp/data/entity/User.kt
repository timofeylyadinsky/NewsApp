package com.example.newsapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userInfo")
data class User(
    @PrimaryKey val uid: Int,
    val isLocked: Boolean,
    val passcode: Int
)
