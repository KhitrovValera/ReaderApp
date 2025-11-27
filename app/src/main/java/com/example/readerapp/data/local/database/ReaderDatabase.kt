package com.example.readerapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.readerapp.data.local.common.ListIntConverter
import com.example.readerapp.data.local.common.ListStringConverter
import com.example.readerapp.data.local.dao.BooksDao
import com.example.readerapp.data.local.dao.UserDao
import com.example.readerapp.data.local.model.BookEntity
import com.example.readerapp.data.local.model.UserEntity

@Database(
    entities = [
        BookEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListIntConverter::class, ListStringConverter::class)
abstract class ReaderDatabase : RoomDatabase() {
    abstract fun bookDao() : BooksDao
    abstract fun userDao() : UserDao

}