package com.danrsy.rgithubuser.ui.followers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.danrsy.rgithubuser.core.domain.usecase.GUUseCase


class FollowersViewModel(private val guUseCase: GUUseCase) : ViewModel() {

    fun followers(username: String) = guUseCase.getFollowers(username).asLiveData()
}