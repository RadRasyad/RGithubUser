package com.danrsy.rgithubuser.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("total_count")
    val totalCount: Int?,
    @field:SerializedName("incomplete_results")
    var incompleteResults: Boolean?,
    var items: ArrayList<User>
)

@Entity(tableName = "users")
data class User(

    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @field:SerializedName("login")
    @ColumnInfo(name = "username")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "img")
    val avatarUrl: String? = null,

    @field:SerializedName("html_url")
    @ColumnInfo(name = "url")
    val htmlUrl: String? = null,

    @field:SerializedName("company")
    @ColumnInfo(name = "company")
    val company: String? = null,

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String? = null,

    @field:SerializedName("location")
    @ColumnInfo(name = "location")
    val location: String? = null,

    @field:SerializedName("email")
    @ColumnInfo(name = "email")
    val email: String? = null,

    @field:SerializedName("public_repos")
    @ColumnInfo(name = "repository")
    val publicRepos: Int? = null,

    @field:SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int? = null,

)
