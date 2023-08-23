package com.danrsy.rgithubuser.core.data.remote.response


import com.google.gson.annotations.SerializedName

data class ListUserResponse(
    @field:SerializedName("total_count")
    val totalCount: Int?,
    var items: ArrayList<UserResponse>
)
