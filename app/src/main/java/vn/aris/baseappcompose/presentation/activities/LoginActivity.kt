package vn.aris.baseappcompose.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.AndroidEntryPoint
import vn.aris.baseappcompose.presentation.activities.base.BaseActivity
import vn.aris.baseappcompose.presentation.activities.base.localContext
import vn.aris.baseappcompose.presentation.composes.workout.WorkoutScreen
import vn.aris.baseappcompose.presentation.ui.theme.BaseAppComposeTheme

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseAppComposeTheme {
                localContext = LocalContext.current
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    WorkoutScreen()
                }
            }
        }
    }
}

