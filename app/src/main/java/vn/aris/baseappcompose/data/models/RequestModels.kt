package vn.aris.baseappcompose.data.models

import com.google.gson.annotations.SerializedName

data class RequestLogin(
    @SerializedName("phone") var phoneNumber: String? = null,
    @SerializedName("area_mobile_code") var phoneCode: String? = null,
)