package com.example.readerapp.data.remote.mapper

import com.example.readerapp.data.remote.model.UserDto
import com.google.firebase.auth.FirebaseUser


fun FirebaseUser.toDto() : UserDto {
    return UserDto(
        uid = uid,
        email = email ?: "",
        name = displayName ?: "",
        image = photoUrl?.toString() ?: "",
        booksId = emptyList()
    )
}