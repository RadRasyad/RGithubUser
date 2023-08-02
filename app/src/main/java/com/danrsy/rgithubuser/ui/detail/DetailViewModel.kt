package com.danrsy.rgithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.data.source.GURepository
import com.danrsy.rgithubuser.data.source.network.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GURepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _errorMgs = MutableLiveData<String>()
    val errorMgs: LiveData<String> = _errorMgs

    suspend fun getUserData(username: String): LiveData<User> {
        _isLoading.value = true
        val userData = MutableLiveData<User>()

        if (repository.getFavoriteDetailUser(username) != null) {
            userData.value = repository.getFavoriteDetailUser(username)
            _isLoading.value = false
            _isError.value = false
        } else {
            val client = ApiConfig.getApiService().getUserDetail(username)
            client.enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                        _isError.value = false
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    _isLoading.value = false
                    _isError.value = true
                    _errorMgs.value = t.message
                    Log.e(TAG, "onFailure: ${t.message.toString()}")

                }
            })
        }

        return userData
    }

    suspend fun checkUser(id: Int) = repository.checkIfExist(id)

    fun addToFavorite(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertFavoriteUser(user)
        }
    }

    fun removeFromFavorite(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavoriteUser(user)
        }
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }

}