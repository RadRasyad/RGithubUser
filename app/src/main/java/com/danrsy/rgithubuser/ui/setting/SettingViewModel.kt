package com.danrsy.rgithubuser.ui.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danrsy.rgithubuser.data.source.GURepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GURepository(application)

    fun getThemeSettings(): LiveData<Int> {
        return repository.getThemeSetting().asLiveData(Dispatchers.IO)
    }

    fun saveThemeSetting(theme: Int) = viewModelScope.launch {
        repository.saveThemeSetting(theme)
    }

}