package com.danrsy.rgithubuser.data.source.local

import androidx.room.*
import com.danrsy.rgithubuser.data.model.User

@Dao
interface UsersDao {

    @Query("SELECT * FROM users ORDER BY username ASC")
    suspend fun getFavoriteListUser(): List<User>

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getFavoriteDetailUser(username: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: User)

    @Delete
    suspend fun deleteFavoriteUser(user: User)

    @Query("SELECT count(*) FROM users WHERE users.id = :id")
    suspend fun checkIfExist(id: Int): Int
}