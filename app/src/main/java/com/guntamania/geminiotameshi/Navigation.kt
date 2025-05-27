package com.guntamania.geminiotameshi

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guntamania.geminiotameshi.baking.BakingScreen
import com.guntamania.geminiotameshi.ui.component.BaseComponent

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreensNavigation.BakingScreenNav.routes
    ) {
        composable(ScreensNavigation.BakingScreenNav.routes) {
            BaseComponent {
                BakingScreen(navController = navController)
            }
        }
    }

}

sealed class ScreensNavigation(
    val routes: String,
    val title: String = "",
) {
    data object NavigationScreen : ScreensNavigation("NavigationScreen")

    data object BakingScreenNav : ScreensNavigation(routes = "BakingScreen", title = "Baking")
}