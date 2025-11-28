package com.example.readerapp.domain.repository

import com.example.readerapp.domain.model.Book
import com.example.readerapp.domain.model.User
import com.example.readerapp.toDomain


interface ReaderRepository {

    suspend fun registerWithEmail(email: String, password: String) : Result<Unit>

    suspend fun loginWithEmail(email: String, password: String) : Result<Unit>

    suspend fun authWithGoogle(idToken: String) : Result<Unit>

    suspend fun updateUser(name: String?, photoUri: String?) : Result<Unit>

    suspend fun getAuthUser() : User?

    suspend fun signOut()

    suspend fun getAllRemoteBook(): List<Book>

    suspend fun getAllUsersRemoteBook(): List<Book>?

    suspend fun getAllFilterUsersBook(searchText : String?) : List<Book>?

    suspend fun deleteBook(bookId: Int)

    suspend fun saveLocalBook(
        remoteId: String,
        title: String,
        author: String,
        localPath: String
    )

    suspend fun addLocalBookToRemote(book: Book)

}