package vn.aris.baseappcompose.utils

import android.app.LocaleManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.LocaleList
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import org.joda.time.DateTime
import vn.aris.baseappcompose.application
import vn.aris.baseappcompose.presentation.activities.base.localContext

object Utils {

    fun isNetworkAvailable(): Boolean {
        (localContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    fun getString(resId: Int) = localContext?.resources?.getString(resId) ?: ""

    fun updateLocale(context: Context, localString: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(localString)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(localString))
        }
    }

    fun parseDateToDateOfWeek(date: String?): String {
        return when (DateTime.parse(date).dayOfWeek) {
            1 -> "MON"
            2 -> "TUE"
            3 -> "WED"
            4 -> "THU"
            5 -> "FRI"
            6 -> "SAT"
            7 -> "SUN"
            else -> ""
        }
    }

    fun pxToDp(context: Context, px: Float): Float {
        val resources = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            px,
            resources.displayMetrics
        )
    }
}