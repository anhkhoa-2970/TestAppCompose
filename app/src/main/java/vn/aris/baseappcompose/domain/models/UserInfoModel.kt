package vn.aris.baseappcompose.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NAME_LOGIN_USER = "LoginUser"
@Entity(tableName = TABLE_NAME_LOGIN_USER)
data class UserInfoModel(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 1,
    val contactName: String,
    val email: String,
    val token: String,
    val phoneNumber: String
)
