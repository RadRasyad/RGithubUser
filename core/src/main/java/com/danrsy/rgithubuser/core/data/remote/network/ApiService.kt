package com.danrsy.rgithubuser.core.data.remote.network

import com.danrsy.rgithubuser.core.BuildConfig
import com.danrsy.rgithubuser.core.data.remote.response.ListUserResponse
import com.danrsy.rgithubuser.core.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: token $secret")
    suspend fun getUsers(): ArrayList<UserResponse>

    @GET("search/users")
    @Headers("Authorization: token $secret")
    suspend fun getSearchUsers(
        @Query(value = "q") query: String
    ): ListUserResponse

    @GET("users/{username}")
    @Headers("Authorization: token $secret")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): UserResponse

    @GET("users/{username}/followers")
    @Headers("Authorization: token $secret")
    suspend fun getFollowers(
        @Path("username") username: String
    ): ArrayList<UserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token $secret")
    suspend fun getFollowing(
        @Path("username") username: String
    ): ArrayList<UserResponse>

    companion object {
        const val secret = BuildConfig.KEY
    }
}