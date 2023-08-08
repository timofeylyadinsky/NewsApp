package com.example.newsapp.data.repository.module

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.db.UserDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideUserDao(userDB: UserDB) = userDB.userDao()

    @Provides
    @Singleton
    fun provideUserDB(@ApplicationContext context: Context) = Room
            .databaseBuilder(context, UserDB::class.java, "userInfo")
            .build()

}