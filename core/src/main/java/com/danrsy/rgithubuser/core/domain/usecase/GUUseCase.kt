package com.danrsy.rgithubuser.core.domain.usecase

import com.danrsy.rgithubuser.core.domain.model.User
import com.danrsy.rgithubuser.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface GUUseCase {
    fun getAllUsers(): Flow<Resource<ArrayList<User>>>

    fun getSearchUsers(query: String): Flow<Resource<ArrayList<User>>>

    fun getFollowers(username: String): Flow<Resource<ArrayList<User>>>

    fun getFollowing(username: String): Flow<Resource<ArrayList<User>>>

    fun getDetailUser(username: String): Flow<Resource<User>>

    fun getAllFavUsers(): Flow<List<User>>

    fun getFavoriteDetailUser(username: String): Flow<User>?

    suspend fun insertFavoriteUser(user: User)

    suspend fun deleteFavoriteUser(user: User)

    suspend fun saveThemeSetting(theme: Int)

    fun getThemeSetting(): Flow<Int>
}