package vn.aris.baseappcompose.utils

import com.google.gson.Gson
import retrofit2.HttpException
import java.net.SocketTimeoutException

fun Throwable.is500InternalError() = this is HttpException && code() == 500
fun Throwable.isTimeoutError() = this is SocketTimeoutException
fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}