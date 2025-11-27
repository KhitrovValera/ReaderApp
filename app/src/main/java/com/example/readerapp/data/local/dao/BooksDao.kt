package com.example.readerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.readerapp.data.local.model.BookEntity

@Dao
interface BooksDao {
    @Query("SELECT * FROM local_books WHERE title LIKE '%' || :searchText || '%' OR author LIKE '%' || :searchText || '%'")
    suspend fun getAllFilterBooks(searchText: String?) : List<BookEntity>

    @Query("SELECT * FROM local_books WHERE id = :bookId")
    suspend fun getBook(bookId : Int) : BookEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Query("DELETE FROM local_books WHERE id = :bookId")
    suspend fun deleteBook(bookId: Int)

    @Query("DELETE FROM local_books")
    suspend fun deleteAllBook()

}