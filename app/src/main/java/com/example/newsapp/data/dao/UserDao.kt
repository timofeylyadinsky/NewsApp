package com.example.newsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.data.entity.User

@Dao
interface UserDao {

    @Query("select * from userInfo where uid=1")
    fun getUser(): List<User>

    @Insert
    fun saveUser(user: User)
}