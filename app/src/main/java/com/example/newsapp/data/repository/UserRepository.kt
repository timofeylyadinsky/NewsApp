package com.example.newsapp.data.repository



import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao){
    fun saveUser(user: User) {
        userDao.saveUser(user)
    }
    fun getUserInfo(): User {
        return userDao.getUser();
    }
}




