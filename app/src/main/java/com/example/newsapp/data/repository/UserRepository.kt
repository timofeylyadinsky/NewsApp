package com.example.newsapp.data.repository


import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun saveUser(user: User) {
        userDao.saveUser(user)
    }

    suspend fun getUserInfo() = userDao.getUser()

}




