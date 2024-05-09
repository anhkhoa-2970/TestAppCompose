package vn.aris.baseappcompose.presentation.composes.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object HomeScreen : Destinations(
        route = "home_screen",
        title = "Home",
        icon = Icons.Outlined.Home
    )

    object CourseScreen : Destinations(
        route = "course_screen",
        title = "Course",
        icon = Icons.Outlined.Favorite
    )

    object Notification : Destinations(
        route = "notification_screen",
        title = "Notification",
        icon = Icons.Outlined.Notifications
    )

}