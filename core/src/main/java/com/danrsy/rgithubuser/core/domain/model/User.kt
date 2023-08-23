package com.danrsy.rgithubuser.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id: Int?,
    val login: String?,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val company: String?,
    val name: String?,
    val location: String?,
    val publicRepos: Int?,
    val followers: Int?,
    val following: Int?,
    var isFavorite: Boolean?
): Parcelable
