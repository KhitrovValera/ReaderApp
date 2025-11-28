package com.example.readerapp.screens.my_books

import androidx.lifecycle.ViewModel
import com.example.readerapp.domain.model.Book
import com.example.readerapp.domain.repository.ReaderRepository
import com.example.readerapp.screens.profile.ProfileViewModel.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class BookViewModel(
    private val repository: ReaderRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<BooksState>(BooksState.Loading)
    val profileState = _profileState.asStateFlow()

    init {

    }




    sealed interface BooksState {
        data class Content(var books: List<Book>) : BooksState
        object Loading : BooksState
        object Empty : BooksState
        data class BookError(var message: String) : BooksState
    }
}