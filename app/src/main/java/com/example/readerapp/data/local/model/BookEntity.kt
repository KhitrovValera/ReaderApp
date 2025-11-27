package com.example.readerapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val remoteId: String,
    val title: String,
    val author: String,
    val localFilePath: String
)
