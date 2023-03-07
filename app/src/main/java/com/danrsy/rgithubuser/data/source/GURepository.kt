package com.danrsy.rgithubuser.data.source

import android.app.Application
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.data.model.UserResponse
import com.danrsy.rgithubuser.data.source.local.UsersDao
import com.danrsy.rgithubuser.data.source.local.UsersDatabase

class GURepository(application: Application) {

    private val dao: UsersDao

    init {
        val database: UsersDatabase = UsersDatabase.getInstance(application)
        dao = database.userDao()
    }


}