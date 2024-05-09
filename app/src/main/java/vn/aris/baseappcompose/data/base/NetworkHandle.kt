package vn.aris.baseappcompose.data.base

import org.json.JSONObject
import retrofit2.Response
import vn.aris.baseappcompose.R
import java.lang.Exception
import vn.aris.baseappcompose.utils.Constants
import vn.aris.baseappcompose.utils.Utils.getString
import vn.aris.baseappcompose.utils.isTimeoutError

suspend fun <T> getNetworkResult(call: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            checkNetworkResult(body)
        } else {
            NetworkResult.Failure(
                message = "unknown error",
                code = Constants.CODE_UNKNOWN
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
        if (e.isTimeoutError()) {
            NetworkResult.Failure(
                message = getString(R.string.time_out),
                code = Constants.CODE_ERROR_INTERNAL_SERVER
            )
        } else {
            NetworkResult.Failure(
                message = e.message.toString(),
                code = Constants.CODE_UNKNOWN
            )
        }
    }
}

private fun <T> checkNetworkResult(body: T?): NetworkResult<T> {
    return if (body != null) {
        NetworkResult.Success("", body)
    } else {
        getNetworkResultFailure()
    }
}

private fun <T> getNetworkResultFailure(response: Response<ApiResponse<T>>): NetworkResult<T> {
    val errorString = response.errorBody()?.string() ?: "{}"
    val jsonObject = JSONObject(errorString)
    if (jsonObject.optString(Constants.MESSAGE).isNotEmpty()) {
        jsonObject.let {
            return (NetworkResult.Failure(
                message = it.optString(Constants.MESSAGE),
                code = response.code()
            ))
        }
    } else {
        val errorJson = JSONObject(errorString).getJSONObject(Constants.MESSAGE)
        val message = errorJson.optString(Constants.MESSAGE)
        return NetworkResult.Failure(message = message, code = response.code())
    }
}

private fun <T> getNetworkResultFailure(
    msg: String = "some error",
    code: Int = Constants.CODE_UNKNOWN
): NetworkResult<T> {
    return NetworkResult.Failure(
        message = msg,
        code = code
    )
}

fun <T> NetworkResult<T>.handleNetworkResult(
    success: (NetworkResult.Success<T>) -> Unit = {},
    fail: (NetworkResult.Failure<T>) -> Unit = {},
    loading: (Boolean) -> Unit = {}
) {
    when (this) {
        is NetworkResult.Loading -> loading(true)
        is NetworkResult.Success -> {
            success(this)
            loading(false)
        }

        is NetworkResult.Failure -> {
            fail(this)
            loading(false)
        }
    }
}