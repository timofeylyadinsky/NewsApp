package com.example.newsapp.data.repository.module

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.db.UserDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideUserDao(userDB: UserDB): UserDao {
        return userDB.userDao()
    }

    @Provides
    @Singleton
    fun provideUserDB(@ApplicationContext context: Context) : UserDB {
        return Room
            .databaseBuilder(context, UserDB::class.java, "userInfo")
            .build()
    }
}