package com.danrsy.rgithubuser.core.domain.usecase

import com.danrsy.rgithubuser.core.domain.model.User
import com.danrsy.rgithubuser.core.domain.repository.IGURepository
import com.danrsy.rgithubuser.core.data.Resource
import kotlinx.coroutines.flow.Flow

class GUInteractor(private val iguRepository: IGURepository):GUUseCase {

    override fun getAllUsers(): Flow<Resource<ArrayList<User>>> = iguRepository.getAllUsers()

    override fun getSearchUsers(query: String): Flow<Resource<ArrayList<User>>> = iguRepository.getSearchUsers(query)

    override fun getFollowers(username: String): Flow<Resource<ArrayList<User>>> = iguRepository.getFollowers(username)

    override fun getFollowing(username: String): Flow<Resource<ArrayList<User>>> = iguRepository.getFollowing(username)

    override fun getDetailUser(username: String): Flow<Resource<User>> = iguRepository.getDetailUser(username)

    override fun getAllFavUsers(): Flow<List<User>> = iguRepository.getAllFavUsers()

    override fun getFavoriteDetailUser(username: String): Flow<User>? = iguRepository.getFavoriteDetailUser(username)

    override suspend fun insertFavoriteUser(user: User) = iguRepository.insertFavoriteUser(user)

    override suspend fun deleteFavoriteUser(user: User) = iguRepository.deleteFavoriteUser(user)

    override suspend fun saveThemeSetting(theme: Int) = iguRepository.saveThemeSetting(theme)

    override fun getThemeSetting() = iguRepository.getThemeSetting()
}