package com.example.myapplicationflow.database

import androidx.room.*
import com.example.myapplicationflow.models.User

@Dao
interface ItemDatabaseDao {
    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Query("SELECT * from users LIMIT 1")
    fun listUsersEmpty(): User?

    @Query("SELECT * FROM users WHERE name LIKE '%' || :searchText || '%'")
    fun searchUsersByNameLetter(searchText: String): List<User>

    @Query("SELECT * FROM users WHERE name = :searchText")
    fun searchUsersByNameWord(searchText: String): List<User>

    @Delete
    fun delete(user: User)
}