package com.example.readerapp.domain.provider

interface FileSourceProvider {
    suspend fun getLocalFilePath(
        bookId: String,
        remoteUrl: String?
    ): String
}