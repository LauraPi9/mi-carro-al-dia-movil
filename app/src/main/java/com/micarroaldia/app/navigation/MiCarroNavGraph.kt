package com.micarroaldia.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.micarroaldia.app.data.VehicleRepository
import com.micarroaldia.app.ui.screens.ConfirmationScreen
import com.micarroaldia.app.ui.screens.DashboardScreen
import com.micarroaldia.app.ui.screens.ForgotPasswordScreen
import com.micarroaldia.app.ui.screens.LoginScreen
import com.micarroaldia.app.ui.screens.NewObligationScreen
import com.micarroaldia.app.ui.screens.RegisterVehicleScreen
import com.micarroaldia.app.ui.screens.VehicleListScreen

object Routes {
    const val LOGIN = "login"
    const val FORGOT_PASSWORD = "forgot_password"
    const val CONFIRMATION = "confirmation"
    const val DASHBOARD = "dashboard"
    const val REGISTER_VEHICLE = "register_vehicle"
    const val VEHICLE_LIST = "vehicle_list"
    const val NEW_OBLIGATION = "new_obligation"
}

private fun NavHostController.logout() {
    navigate(Routes.LOGIN) {
        popUpTo(Routes.DASHBOARD) { inclusive = true }
    }
}

@Composable
fun MiCarroNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onForgotPasswordClick = { navController.navigate(Routes.FORGOT_PASSWORD) },
                onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
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
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onLogoutClick = { navController.logout() },
                onRegisterVehicleClick = { navController.navigate(Routes.REGISTER_VEHICLE) },
                onSeeAllVehiclesClick = { navController.navigate(Routes.VEHICLE_LIST) },
                onNewObligationClick = { navController.navigate(Routes.NEW_OBLIGATION) }
            )
        }
        composable(Routes.REGISTER_VEHICLE) {
            RegisterVehicleScreen(
                onBackClick = { navController.popBackStack() },
                onLogoutClick = { navController.logout() },
                onVehicleSaved = { vehicle ->
                    VehicleRepository.add(vehicle)
                    navController.navigate(Routes.VEHICLE_LIST) {
                        popUpTo(Routes.DASHBOARD)
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.NEW_OBLIGATION) {
            NewObligationScreen(
                vehicles = VehicleRepository.vehicles,
                onExit = { navController.popBackStack() },
                onLogoutClick = { navController.logout() },
                onAlarmCreated = { navController.popBackStack(Routes.DASHBOARD, inclusive = false) }
            )
        }
        composable(Routes.VEHICLE_LIST) {
            VehicleListScreen(
                vehicles = VehicleRepository.vehicles,
                onBackClick = { navController.popBackStack() },
                onLogoutClick = { navController.logout() },
                onAddVehicleClick = { navController.navigate(Routes.REGISTER_VEHICLE) },
                onGoHomeClick = { navController.popBackStack(Routes.DASHBOARD, inclusive = false) }
            )
        }
    }
}
