package vn.aris.baseappcompose.data.models

import com.google.gson.annotations.SerializedName


data class UserInfoEntity(
    @SerializedName("contact_name") val contactName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("token") val token: String?,
    @SerializedName("phone") val phoneNumber: String?
)
