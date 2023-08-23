package com.danrsy.rgithubuser.core.data.remote

import android.util.Log
import com.danrsy.rgithubuser.core.data.remote.network.ApiResponse
import com.danrsy.rgithubuser.core.data.remote.network.ApiService
import com.danrsy.rgithubuser.core.data.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {


    suspend fun getAllGithubUsers(): Flow<ApiResponse<ArrayList<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getUsers()
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchUsers(query: String): Flow<ApiResponse<ArrayList<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getSearchUsers(query)
                val dataArray = response.items
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.items))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(username: String): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val response = apiService.getUserDetail(username)
                if (response!=null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowers(username: String): Flow<ApiResponse<ArrayList<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowers(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowing(username: String): Flow<ApiResponse<ArrayList<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowing(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}