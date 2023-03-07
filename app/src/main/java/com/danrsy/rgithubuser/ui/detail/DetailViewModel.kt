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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel() : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _errorMgs = MutableLiveData<String>()
    val errorMgs: LiveData<String> = _errorMgs

    fun getUserData(username: String): LiveData<User> {
        _isLoading.value = true
        val userData = MutableLiveData<User>()
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {
                    userData.value = response.body()
                    _isError.value = false
                } else {
                    _isError.value = true
                    _errorMgs.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
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
        return userData
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }

}