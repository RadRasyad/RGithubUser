package com.danrsy.rgithubuser.ui.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.danrsy.rgithubuser.core.domain.usecase.GUUseCase

class FollowingViewModel(private val guUseCase: GUUseCase) : ViewModel() {

    fun following(username: String) = guUseCase.getFollowing(username).asLiveData()
}