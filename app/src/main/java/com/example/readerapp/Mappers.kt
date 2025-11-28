package com.example.readerapp

import com.example.readerapp.data.local.model.BookEntity
import com.example.readerapp.data.local.model.UserEntity
import com.example.readerapp.data.remote.model.BookDto
import com.example.readerapp.data.remote.model.UserDto
import com.example.readerapp.domain.model.Book
import com.example.readerapp.domain.model.User
import java.util.UUID

fun BookEntity.toDomain() : Book {
    return Book(
        title = title,
        author = author,
        localFilePath = localFilePath,
        uri = ""
    )
}

fun Book.toEntity() : BookEntity {
    return BookEntity(
        remoteId = remoteId,
        title = title,
        author = author,
        localFilePath = localFilePath ?: "не сыграет роль",
    )
}

fun UserDto.toEntity() : UserEntity {
    return UserEntity(
        uid = uid,
        email = email,
        name = name,
        image = image,
        booksId = emptyList(),
        remoteBooksId = booksId
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

fun Book.toDto() : BookDto {
    return BookDto(
        bookId = if (remoteId == "" || remoteId == null) UUID.randomUUID().toString() else remoteId,
        title = title,
        author = author,
        uri = uri ?: ""
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