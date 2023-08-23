package com.danrsy.rgithubuser.di

import com.danrsy.rgithubuser.core.domain.usecase.GUInteractor
import com.danrsy.rgithubuser.core.domain.usecase.GUUseCase
import com.danrsy.rgithubuser.ui.MainViewModel
import com.danrsy.rgithubuser.ui.detail.DetailViewModel
import com.danrsy.rgithubuser.ui.followers.FollowersViewModel
import com.danrsy.rgithubuser.ui.following.FollowingViewModel
import com.danrsy.rgithubuser.ui.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GUUseCase> { GUInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FollowersViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}