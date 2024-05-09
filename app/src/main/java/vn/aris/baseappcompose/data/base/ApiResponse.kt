package vn.aris.baseappcompose.data.base

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("result")
    val result: String = "",

    @SerializedName("message")
    val message: String = "",

    @SerializedName("day_data")
    val dayData: List<T>? = null
)
