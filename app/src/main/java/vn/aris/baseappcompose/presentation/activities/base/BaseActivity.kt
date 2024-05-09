package vn.aris.baseappcompose.presentation.activities.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import vn.aris.baseappcompose.domain.models.CommonItemModel
import vn.aris.baseappcompose.utils.Constants
import vn.aris.baseappcompose.utils.SavedStore
import vn.aris.baseappcompose.utils.Utils

@SuppressLint("StaticFieldLeak")
var localContext: Context? = null
var baseActivity: BaseActivity? = null
open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = this
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)

        val languageModel = if (SavedStore.getString(Constants.PREFS_LANGUAGE_MODEL, "").isEmpty()) {
            CommonItemModel.getLanguageList()[0]
        } else {
            Gson().fromJson(
                SavedStore.getString(Constants.PREFS_LANGUAGE_MODEL, ""), CommonItemModel::class.java
            )
        }
        newBase?.let { Utils.updateLocale(it, languageModel.getLanguageCode()) }
    }
}