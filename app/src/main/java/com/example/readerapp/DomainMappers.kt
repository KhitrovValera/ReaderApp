package com.example.readerapp

import com.example.readerapp.data.local.model.BookEntity
import com.example.readerapp.data.local.model.UserEntity
import com.example.readerapp.data.remote.model.BookDto
import com.example.readerapp.data.remote.model.UserDto
import com.example.readerapp.domain.model.Book
import com.example.readerapp.domain.model.User

fun BookEntity.toDomain() : Book {
    return Book(
        id = id,
        remoteId = remoteId,
        title = title,
        author = author,
        localFilePath = localFilePath,
        uri = ""
    )
}

fun BookDto.toDomain() : Book {
    return Book(
        id = 0,
        remoteId = bookId,
        title = title,
        author = author,
        localFilePath = "",
        uri = uri
    )
}

fun UserEntity.toDomain(): User {
    return User(
        email = email,
        name = name,
        image = image
    )
}

fun UserDto.toDomain() : User {
    return User(
        email = email,
        name = name,
        image = image
    )
}