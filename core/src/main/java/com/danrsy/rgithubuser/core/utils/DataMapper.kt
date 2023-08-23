package com.danrsy.rgithubuser.core.utils

import com.danrsy.rgithubuser.core.data.local.entity.UserEntity
import com.danrsy.rgithubuser.core.data.remote.response.UserResponse
import com.danrsy.rgithubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapResponsesToDomain(input: ArrayList<UserResponse>): Flow<ArrayList<User>> {
        val dataArray = ArrayList<User>()
        input.map {
            val user = User(
                it.id,
                it.login,
                it.avatarUrl,
                it.htmlUrl,
                it.company,
                it.name,
                it.location,
                it.publicRepos,
                it.followers,
                it.following,
                false
            )
            dataArray.add(user)
        }
        return flowOf(dataArray)
    }

    fun mapResponseToDomain(input: UserResponse): Flow<User> {
        return flowOf(
            User(
                input.id,
                input.login,
                input.avatarUrl,
                input.htmlUrl,
                input.company,
                input.name,
                input.location,
                input.publicRepos,
                input.followers,
                input.following,
                false
            )
        )
    }

    fun mapEntityToDomain(input: List<UserEntity>): List<User> =
        input.map {
            User(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl,
                company = it.company,
                name = it.name,
                location = it.location,
                publicRepos = it.publicRepos,
                followers = it.followers,
                following = it.following,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntityToDomain(input: UserEntity?) = User(
                id = input?.id,
                login = input?.login,
                avatarUrl = input?.avatarUrl,
                htmlUrl = input?.htmlUrl,
                company = input?.company,
                name = input?.name,
                location = input?.location,
                publicRepos = input?.publicRepos,
                followers = input?.followers,
                following = input?.following,
                isFavorite = input?.isFavorite
            )

    fun mapDomainToEntity(input: User) = UserEntity(
        id = input.id,
        login = input.login,
        avatarUrl = input.avatarUrl,
        htmlUrl = input.htmlUrl,
        company = input.company,
        name = input.name,
        location = input.location,
        publicRepos = input.publicRepos,
        followers = input.followers,
        following = input.following,
        isFavorite = input.isFavorite
    )
}