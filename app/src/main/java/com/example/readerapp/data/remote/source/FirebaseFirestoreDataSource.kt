package com.example.readerapp.data.remote.source

import com.example.readerapp.data.remote.model.BookDto
import com.example.readerapp.data.remote.model.UserDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getAllBooks() : List<BookDto> {
        val snapshot = firestore.collection("books")
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(BookDto::class.java) }
    }

    suspend fun getAllUsersBooks(userId: String): List<BookDto> {
        val userSnapshot = firestore.collection("users")
            .document(userId)
            .get()
            .await()

        val booksId = userSnapshot.get("booksId") as? List<String> ?: emptyList()

        return booksId.mapNotNull { bookId ->
            firestore.collection("books")
                .document(bookId)
                .get()
                .await()
                .toObject(BookDto::class.java)
        }
    }

    suspend fun getBookById(bookId: String): BookDto? {
        return try {
            firestore.collection("books")
                .document(bookId)
                .get()
                .await()
                .toObject(BookDto::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun addBook(book: BookDto) {
        firestore.collection("books")
            .document(book.bookId)
            .set(book)
            .await()
    }

    suspend fun addBookToUser(userId: String, bookId: String) {
        val userDoc = firestore.collection("users").document(userId)

        firestore.runTransaction { transaction ->
            val userSnapshot = transaction.get(userDoc)

            val booksId = userSnapshot.get("booksId") as? MutableList<String> ?: mutableListOf()

            if (!booksId.contains(bookId)) booksId.add(bookId)

            transaction.update(userDoc, "booksId", booksId)

        }.await()

    }

    suspend fun addUser(user: UserDto) {
        firestore.collection("users")
            .document(user.uid)
            .set(user)
            .await()
    }
}