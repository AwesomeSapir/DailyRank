package com.sapreme.dailyrank.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sapreme.dailyrank.ui.screen.DashboardScreen
import com.sapreme.dailyrank.ui.screen.GroupsScreen
import com.sapreme.dailyrank.ui.screen.MainScreen
import com.sapreme.dailyrank.ui.screen.OnboardingScreen
import com.sapreme.dailyrank.ui.screen.SignInScreen
import com.sapreme.dailyrank.ui.screen.SplashScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(Route.Splash.route) {
            SplashScreen(
                onMain = {
                    navController.navigate(Route.Main.route) {
                        popUpTo(Route.Splash.route) { inclusive = true }
                    }
                },
                onOnboarding = {
                    navController.navigate(Route.Onboarding.route) {
                        popUpTo(Route.Splash.route) { inclusive = true }
                    }
                },
                onSignIn = {
                    navController.navigate(Route.SignIn.route) {
                        popUpTo(Route.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Route.SignIn.route) {
            SignInScreen(
                onSignedIn = {
                    navController.popBackStack(Route.Splash.route, inclusive = false)
                }
            )
        }
        composable(Route.Onboarding.route) {
            OnboardingScreen(
                onFinished = {
                    navController.navigate(Route.Main.route) {
                        popUpTo(Route.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Route.Main.route) {
            MainScreen()
        }
    }
}

fun NavGraphBuilder.mainNavGraph() {
    composable(Route.Dashboard.route) { DashboardScreen() }
    composable(Route.Groups.route) { GroupsScreen() }
}