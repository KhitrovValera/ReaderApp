package com.example.readerapp.di

import android.content.Context
import androidx.room.Room
import com.example.readerapp.data.local.dao.BooksDao
import com.example.readerapp.data.local.dao.UserDao
import com.example.readerapp.data.local.database.ReaderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "reader_db"

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : ReaderDatabase {
        return Room.databaseBuilder(
            context,
            ReaderDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideBookDao(
        readerDatabase: ReaderDatabase
    ) : UserDao {
        return readerDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(
        readerDatabase: ReaderDatabase
    ) : BooksDao {
        return readerDatabase.bookDao()
    }


}
