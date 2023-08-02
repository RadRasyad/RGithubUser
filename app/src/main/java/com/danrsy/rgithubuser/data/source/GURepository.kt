package com.danrsy.rgithubuser.data.source

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.data.source.datastore.GUDataStore
import com.danrsy.rgithubuser.data.source.local.UsersDao
import com.danrsy.rgithubuser.data.source.local.UsersDatabase

class GURepository(application: Application) {

    private val dao: UsersDao
    private val dataStore: GUDataStore

    init {
        val database: UsersDatabase = UsersDatabase.getInstance(application)
        dao = database.userDao()
        dataStore = GUDataStore.getInstance(application)
    }

    suspend fun getAllFavorite(): LiveData<List<User>> {
        val userList = MutableLiveData<List<User>>()

        userList.value = dao.getFavoriteListUser()

        return userList
    }

    suspend fun getFavoriteDetailUser(username: String): User? = dao.getFavoriteDetailUser(username)

    suspend fun insertFavoriteUser(user: User) = dao.insertFavoriteUser(user)

    suspend fun deleteFavoriteUser(user: User) = dao.deleteFavoriteUser(user)

    suspend fun checkIfExist(id: Int) = dao.checkIfExist(id)

    suspend fun saveThemeSetting(theme: Int) = dataStore.saveThemeSetting(theme)

    fun getThemeSetting() = dataStore.getThemeSetting()
}