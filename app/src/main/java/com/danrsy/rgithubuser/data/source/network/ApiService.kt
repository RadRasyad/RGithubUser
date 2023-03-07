package com.danrsy.rgithubuser.data.source.network

import com.danrsy.rgithubuser.BuildConfig
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: token $secret")
    fun getUsers(): Call<ArrayList<User>>

    @GET("search/users")
    @Headers("Authorization: token $secret")
    fun getSearchUsers(
        @Query(value = "q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $secret")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $secret")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $secret")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    companion object {
        const val secret = BuildConfig.KEY
    }
}