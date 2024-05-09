package vn.aris.baseappcompose

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

var application: MainApplication? = null

@HiltAndroidApp
class MainApplication : Application() {
	private val TAG = "MainApplication"


	override fun onCreate() {
		super.onCreate()
		application = this
	}

	override fun attachBaseContext(base: Context?) {
		super.attachBaseContext(base)
	}
}
