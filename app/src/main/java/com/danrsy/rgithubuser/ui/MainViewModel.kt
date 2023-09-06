package com.danrsy.rgithubuser.ui


import androidx.lifecycle.*
import com.danrsy.rgithubuser.core.domain.usecase.GUUseCase

class MainViewModel(private val guUseCase: GUUseCase) : ViewModel() {

    init {
        getUsers()
    }
    fun getUsers() = guUseCase.getAllUsers().asLiveData()
    fun getSearchUsers(query: String) = guUseCase.getSearchUsers(query).asLiveData()


}