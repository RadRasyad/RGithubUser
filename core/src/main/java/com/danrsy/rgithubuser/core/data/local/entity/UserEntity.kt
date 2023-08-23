package com.danrsy.rgithubuser.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int?,

    @ColumnInfo(name = "username")
    val login: String?,

    @ColumnInfo(name = "img")
    val avatarUrl: String?,

    @ColumnInfo(name = "url")
    val htmlUrl: String?,

    @ColumnInfo(name = "company")
    val company: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "repository")
    val publicRepos: Int?,

    @ColumnInfo(name = "followers")
    val followers: Int?,

    @ColumnInfo(name = "following")
    val following: Int?,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean?

)
