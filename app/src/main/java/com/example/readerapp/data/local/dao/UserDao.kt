package com.example.readerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.readerapp.data.local.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getCurrentUser(): UserEntity?

    @Query("SELECT * FROM user WHERE uid = :userUid")
    suspend fun getUserBooksId(userUid: String) : UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}