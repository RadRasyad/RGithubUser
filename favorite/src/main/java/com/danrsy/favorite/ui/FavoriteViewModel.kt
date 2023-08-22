package com.danrsy.favorite.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.danrsy.rgithubuser.core.domain.usecase.GUUseCase

class FavoriteViewModel(guUseCase: GUUseCase) : ViewModel() {

    val favoriteData = guUseCase.getAllFavUsers().asLiveData()

}