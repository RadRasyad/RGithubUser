package com.danrsy.rgithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.data.source.GURepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GURepository(application)

    suspend fun getAllFavorite(): LiveData<List<User>> = repository.getAllFavorite()

}