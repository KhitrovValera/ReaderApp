package com.example.readerapp.domain.usecase

import com.example.readerapp.domain.provider.FileSourceProvider
import com.example.readerapp.domain.repository.ReaderRepository


class AddBookUseCase(
    private val readerRepository: ReaderRepository,
    private val fileSourceProvider: FileSourceProvider
) {

    suspend operator fun  invoke(bookId: String, remoteUrl: String) {
        val book = readerRepository
    }
}