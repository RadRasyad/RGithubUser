package com.danrsy.rgithubuser.core.data.remote.network

import com.danrsy.rgithubuser.core.data.remote.response.ListUserResponse
import com.danrsy.rgithubuser.core.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): ArrayList<UserResponse>

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query(value = "q") query: String
    ): ListUserResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): UserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): ArrayList<UserResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): ArrayList<UserResponse>

}