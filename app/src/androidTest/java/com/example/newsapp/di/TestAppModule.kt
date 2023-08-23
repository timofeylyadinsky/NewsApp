package com.example.newsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.db.UserDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {
    //    @Provides
//    fun provideUserDao(userDB: UserDB) = userDB.userDao()
//
    @Provides
    @Singleton
    fun provideInMemoryUserDB(app: Application) = Room
        .inMemoryDatabaseBuilder(app, UserDB::class.java)
        .build()
}