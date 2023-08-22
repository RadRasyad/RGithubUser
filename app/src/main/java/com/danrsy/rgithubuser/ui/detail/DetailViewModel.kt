package com.danrsy.rgithubuser.ui.detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danrsy.rgithubuser.core.domain.model.User
import com.danrsy.rgithubuser.core.domain.usecase.GUUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val guUseCase: GUUseCase) : ViewModel() {

    fun detailUsers(username: String) = guUseCase.getDetailUser(username).asLiveData()

    fun getDetailState(username: String) = guUseCase.getFavoriteDetailUser(username)?.asLiveData()

    fun insertFavorite(user: User) = viewModelScope.launch {
        guUseCase.insertFavoriteUser(user)
    }

    fun deleteFavorite(user: User) = viewModelScope.launch {
        guUseCase.deleteFavoriteUser(user)
    }

}