package com.danrsy.rgithubuser.ui

import android.util.Log
import androidx.lifecycle.*
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.data.model.UserResponse
import com.danrsy.rgithubuser.data.source.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _userList = MutableLiveData<ArrayList<User>>()
    val userList: LiveData<ArrayList<User>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _errorMgs = MutableLiveData<String>()
    val errorMgs: LiveData<String> = _errorMgs

    init {
        getUsers()
    }

    private fun getUsers() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userList.value = response.body()
                    _isError.value = false
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _errorMgs.value = t.message
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

    }

    fun getSearchUsers(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _userList.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}