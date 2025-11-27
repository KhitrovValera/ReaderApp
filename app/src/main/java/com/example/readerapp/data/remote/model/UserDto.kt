package com.example.readerapp.data.remote.model

data class UserDto(
    var uid: String = "",
    var email: String = "",
    var name: String = "",
    var image: String = "",
    var booksId: List<String> = emptyList()
)
