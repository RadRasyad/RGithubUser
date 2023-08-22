package com.danrsy.rgithubuser.core.data

import com.danrsy.rgithubuser.core.data.local.LocalDataSource
import com.danrsy.rgithubuser.core.data.local.datastore.GUDataStore
import com.danrsy.rgithubuser.core.data.remote.RemoteDataSource
import com.danrsy.rgithubuser.core.data.remote.network.ApiResponse
import com.danrsy.rgithubuser.core.data.remote.response.UserResponse
import com.danrsy.rgithubuser.core.domain.model.User
import com.danrsy.rgithubuser.core.domain.repository.IGURepository
import com.danrsy.rgithubuser.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GURepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStore: GUDataStore
) : IGURepository {

    override fun getAllUsers(): Flow<Resource<ArrayList<User>>> =
        object :
            NetworkResource<ArrayList<User>, ArrayList<UserResponse>>() {
            override fun loadFromNetwork(data: ArrayList<UserResponse>): Flow<ArrayList<User>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<ArrayList<UserResponse>>> =
                remoteDataSource.getAllGithubUsers()

        }.asFlow()

    override fun getSearchUsers(query: String): Flow<Resource<ArrayList<User>>> =
        object :
            NetworkResource<ArrayList<User>, ArrayList<UserResponse>>() {
            override fun loadFromNetwork(data: ArrayList<UserResponse>): Flow<ArrayList<User>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<ArrayList<UserResponse>>> =
                remoteDataSource.getSearchUsers(query)

        }.asFlow()

    override fun getFollowers(username: String): Flow<Resource<ArrayList<User>>> =
        object :
            NetworkResource<ArrayList<User>, ArrayList<UserResponse>>() {
            override fun loadFromNetwork(data: ArrayList<UserResponse>): Flow<ArrayList<User>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<ArrayList<UserResponse>>> =
                remoteDataSource.getFollowers(username)

        }.asFlow()

    override fun getFollowing(username: String): Flow<Resource<ArrayList<User>>> =
        object :
            NetworkResource<ArrayList<User>, ArrayList<UserResponse>>() {
            override fun loadFromNetwork(data: ArrayList<UserResponse>): Flow<ArrayList<User>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<ArrayList<UserResponse>>> =
                remoteDataSource.getFollowing(username)

        }.asFlow()

    override fun getDetailUser(username: String): Flow<Resource<User>> {
        return object : NetworkResource<User, UserResponse>() {
            override fun loadFromNetwork(data: UserResponse): Flow<User> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.getUserDetail(username)
            }

        }.asFlow()
    }

    override fun getAllFavUsers(): Flow<List<User>> {
        return localDataSource.getFavoriteUsers().map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun getFavoriteDetailUser(username: String): Flow<User>? {
        return localDataSource.getFavoriteDetailUser(username)?.map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override suspend fun insertFavoriteUser(user: User) {
        val domainUser = DataMapper.mapDomainToEntity(user)
        return localDataSource.insertFavUser(domainUser)
    }

    override suspend fun deleteFavoriteUser(user: User) {
        val domainUser = DataMapper.mapDomainToEntity(user)
        return localDataSource.deleteFavUser(domainUser)
    }

    override suspend fun saveThemeSetting(theme: Int) = dataStore.saveThemeSetting(theme)
    override fun getThemeSetting() = dataStore.getThemeSetting()
}