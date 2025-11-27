package com.example.readerapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String = "current",
    val uid: String,
    val email: String,
    val name: String,
    val image: String,
    val booksId: List<Int> = emptyList(),
    val remoteBooksId: List<String> = emptyList()
)