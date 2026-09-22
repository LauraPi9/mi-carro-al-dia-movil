package com.micarroaldia.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.micarroaldia.app.ui.screens.ConfirmationScreen
import com.micarroaldia.app.ui.screens.ForgotPasswordScreen
import com.micarroaldia.app.ui.screens.LoginScreen

object Routes {
    const val LOGIN = "login"
    const val FORGOT_PASSWORD = "forgot_password"
    const val CONFIRMATION = "confirmation"
}

@Composable
fun MiCarroNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onForgotPasswordClick = { navController.navigate(Routes.FORGOT_PASSWORD) }
            )
        }
        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onSendEmailClick = { navController.navigate(Routes.CONFIRMATION) }
            )
        }
        composable(Routes.CONFIRMATION) {
            ConfirmationScreen(
                onCloseClick = {
                    navController.popBackStack(Routes.LOGIN, inclusive = false)
                }
            )
        }
    }
}
