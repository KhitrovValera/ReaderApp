package com.example.readerapp.data.repository

import android.util.Log
import com.example.readerapp.data.local.dao.BooksDao
import com.example.readerapp.data.local.dao.UserDao
import com.example.readerapp.data.local.model.BookEntity
import com.example.readerapp.data.remote.mapper.toDto
import com.example.readerapp.data.remote.source.FirebaseAuthDataSource
import com.example.readerapp.data.remote.source.FirebaseFirestoreDataSource
import com.example.readerapp.domain.model.Book
import com.example.readerapp.domain.model.User
import com.example.readerapp.domain.repository.ReaderRepository
import com.example.readerapp.toDomain
import com.example.readerapp.toDto
import com.example.readerapp.toEntity
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class ReaderRepositoryImpl @Inject constructor(
    private val bookDao: BooksDao,
    private val userDao: UserDao,
    private val firebase: FirebaseFirestoreDataSource,
    private val auth: FirebaseAuthDataSource
) : ReaderRepository {

    private suspend fun handleAuthResult(result: Result<FirebaseUser>, isNewUser: Boolean) : Result<Unit> {
        return result.fold(
            onSuccess = {
                try {
                    val userDto = result.getOrNull()?.toDto() ?: return Result.failure(Exception("user is null"))

                    if (isNewUser) firebase.addUser(userDto)

                    userDao.insertUser(userDto.toEntity())

                    Result.success(Unit)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            },
            onFailure = {
                Result.failure(it)
            }
        )

    }

    override suspend fun registerWithEmail(email: String, password: String) : Result<Unit> {
        val regResult = auth.createUserWithEmail(email, password)

        return handleAuthResult(regResult, true)
    }

    override suspend fun loginWithEmail(email: String, password: String) : Result<Unit> {
        val logResult = auth.signInWithEmail(email, password)

        return handleAuthResult(logResult, false)
    }

    override suspend fun authWithGoogle(idToken: String) : Result<Unit> {
        val regResult = auth.authWithGoogle(idToken)

        return handleAuthResult(regResult, true)
    }

    override suspend fun updateUser(
        name: String?,
        photoUri: String?
    ): Result<Unit> {

        Log.d("gdsfgsd", "${name}")
        userDao.getCurrentUser()?.let {
            userDao.insertUser(it.copy(
                name = name ?: it.name,
                image = photoUri ?: it.image)
            )
        }
        return auth.updateUserProfile(name, photoUri)
    }

    override suspend fun getAuthUser() : User? {
        val user = userDao.getCurrentUser()

        return if (auth.getCurrentUser() == null || user == null)  null
        else user.toDomain()

    }

    override suspend fun signOut() {
        auth.signOut()
        bookDao.deleteAllBook()
        userDao.deleteUser()
    }

    override suspend fun getAllRemoteBook(): List<Book> {
        val books = firebase.getAllBooks()

        return books.map { it.toDomain() }
    }

    override suspend fun getAllUsersRemoteBook(): List<Book>? {
        val user = userDao.getCurrentUser()

        return if (auth.getCurrentUser() == null || user == null)  null
        else {
            val books = firebase.getAllUsersBooks(user.uid)

            books.map { it.toDomain() }
        }
    }

    override suspend fun getAllFilterUsersBook(searchText: String?): List<Book>? {
        val user = userDao.getCurrentUser()

        return if (auth.getCurrentUser() == null || user == null)  null
        else {
            val books = bookDao.getAllFilterBooks(searchText)
            books.map { it.toDomain() }
        }
    }

    override suspend fun deleteBook(bookId: Int) {
        bookDao.deleteBook(bookId)
    }

    override suspend fun saveLocalBook(
        remoteId: String,
        title: String,
        author: String,
        localPath: String
    ) {
        bookDao.insertBook(
            BookEntity(
                remoteId = remoteId,
                title = title,
                author = author,
                localFilePath = localPath
            )
        )
    }

    override suspend fun addLocalBookToRemote(book: Book) {

        val user = userDao.getCurrentUser()

        if (auth.getCurrentUser() == null || user == null)  return
        else {
            val dtoBook = book.toDto()

            firebase.addBookToUser(user.uid, dtoBook)

            val localBook = book.toEntity().copy(remoteId = dtoBook.bookId)

            bookDao.insertBook(localBook)
            userDao.insertUser(user.copy(remoteBooksId = user.remoteBooksId + dtoBook.bookId, booksId = user.booksId + localBook.id))

        }
    }

}