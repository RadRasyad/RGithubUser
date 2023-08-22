package com.danrsy.rgithubuser.core.data.local.room

import androidx.room.*
import com.danrsy.rgithubuser.core.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query("SELECT * FROM users ORDER BY username ASC")
    fun getFavoriteListUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getFavoriteDetailUser(username: String): Flow<UserEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: UserEntity)

    @Delete
    suspend fun deleteFavoriteUser(user: UserEntity)

}