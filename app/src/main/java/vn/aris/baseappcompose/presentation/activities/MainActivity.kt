package vn.aris.baseappcompose.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.aris.baseappcompose.presentation.activities.base.BaseActivity
import vn.aris.baseappcompose.presentation.activities.base.localContext
import vn.aris.baseappcompose.presentation.composes.bottomNav.BottomBar
import vn.aris.baseappcompose.presentation.ui.theme.BaseAppComposeTheme

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            localContext = LocalContext.current
            BaseAppComposeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController: NavHostController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            BottomBar(
                                navController = navController,
                                modifier = Modifier
                            )
                        },
                        topBar = {

                        }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier.padding(paddingValues)
                        ) {

                        }
                    }
                }
            }
        }
    }
}
