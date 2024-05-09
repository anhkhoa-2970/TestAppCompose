package vn.aris.baseappcompose.presentation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.aris.baseappcompose.R
import vn.aris.baseappcompose.presentation.activities.base.BaseActivity
import vn.aris.baseappcompose.presentation.ui.theme.BaseAppComposeTheme
import vn.aris.baseappcompose.presentation.ui.theme.colorMain
import vn.aris.baseappcompose.utils.Constants
import vn.aris.baseappcompose.utils.SavedStore

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen()
        }
        lifecycleScope.launch {
            delay(2500)
            if (SavedStore.getBoolean(Constants.PREFS_LOGIN, false)) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        }
    }

    @Composable
    fun SplashScreen() {
        val systemUiController: SystemUiController = rememberSystemUiController()
        LaunchedEffect(Unit) {
            systemUiController.setStatusBarColor(color = colorMain)
        }
        BaseAppComposeTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.color_main)),
                    contentAlignment = Alignment.Center
                ) {

                }
            }
        }
    }
}

