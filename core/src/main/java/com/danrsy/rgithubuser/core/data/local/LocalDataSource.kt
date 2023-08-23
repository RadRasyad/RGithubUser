package com.danrsy.rgithubuser.core.data.local

import com.danrsy.rgithubuser.core.data.local.entity.UserEntity
import com.danrsy.rgithubuser.core.data.local.room.UsersDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val usersDao: UsersDao) {

    fun getFavoriteUsers(): Flow<List<UserEntity>> = usersDao.getFavoriteListUser()

    fun getFavoriteDetailUser(username: String): Flow<UserEntity>? = usersDao.getFavoriteDetailUser(username)

    suspend fun insertFavUser(user: UserEntity) = usersDao.insertFavoriteUser(user)

    suspend fun deleteFavUser(user: UserEntity) = usersDao.deleteFavoriteUser(user)

}