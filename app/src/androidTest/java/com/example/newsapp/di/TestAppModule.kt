package com.example.newsapp.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.db.UserDB
import com.example.newsapp.data.repository.module.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

//@Module
//@TestInstallIn(components = [SingletonComponent::class], replaces = [DatabaseModule::class])
//class TestAppModule {
//    //    @Provides
////    fun provideUserDao(userDB: UserDB) = userDB.userDao()
////
//    @Provides
//    @Singleton
//    fun provideInMemoryUserDB(app: Application) = Room
//        .inMemoryDatabaseBuilder(app, UserDB::class.java)
//        .build()
//}