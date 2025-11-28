package com.example.readerapp.screens.download_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.domain.model.Book
import com.example.readerapp.domain.repository.ReaderRepository
import com.example.readerapp.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadBookViewModel @Inject constructor(
    private val repository: ReaderRepository
): ViewModel() {

    fun addBook(
        author: String,
        title: String,
        uri: String = "",
        localPath: String
    ) {
        val book = Book(
            null,
            title = title,
            author = author,
            localFilePath = localPath,
            uri = uri,
        )

        viewModelScope.launch {
            repository.addLocalBookToRemote(book)
        }
    }



}