package vn.aris.baseappcompose.data.models

import com.google.gson.annotations.SerializedName


data class CourseEntity(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
)
