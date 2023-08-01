package com.example.newsapp.data.repository


import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.db.UserDB
import com.example.newsapp.data.entity.User


fun saveUser(user: User, context: Context) {
    val db = Room
        .databaseBuilder(context, UserDB::class.java, "userInfo")
        .build()
    val userDao = db.userDao()
    userDao.saveUser(user)
}

fun getUserInfo(context: Context): User {
    val db = Room
        .databaseBuilder(context, UserDB::class.java, "userInfo")
        .build()
    val userDao = db.userDao()
    return userDao.getUser();
}
