package com.danrsy.rgithubuser.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danrsy.rgithubuser.core.domain.usecase.GUUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(private val guUseCase: GUUseCase) : ViewModel() {


    fun getThemeSettings(): LiveData<Int> {
        return guUseCase.getThemeSetting().asLiveData(Dispatchers.IO)
    }

    fun saveThemeSetting(theme: Int) = viewModelScope.launch {
        guUseCase.saveThemeSetting(theme)
    }

}