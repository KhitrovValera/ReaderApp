package com.example.readerapp.domain.model

data class Book(
    val remoteId: String?,
    val title: String,
    val author: String,
    val localFilePath: String?,
    val uri: String?
)