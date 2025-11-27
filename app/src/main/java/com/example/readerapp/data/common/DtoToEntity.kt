package com.example.readerapp.data.common

import com.example.readerapp.data.local.model.BookEntity
import com.example.readerapp.data.local.model.UserEntity
import com.example.readerapp.data.remote.model.BookDto
import com.example.readerapp.data.remote.model.UserDto

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

//fun BookDto.toEntity() : BookEntity {
//    return BookEntity(
//        remoteId = bookId,
//        title = title,
//        author = author,
//        localFilePath = "",
//        uri =
//    )
//}