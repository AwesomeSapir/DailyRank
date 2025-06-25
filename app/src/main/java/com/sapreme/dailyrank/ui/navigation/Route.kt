package com.sapreme.dailyrank.ui.navigation

sealed class Route(val route: String) {
    object Splash : Route(Routes.SPLASH)
    object Onboarding : Route(Routes.ONBOARDING)

    object SignIn : Route(Routes.SIGN_IN)

    object Main : Route(Routes.MAIN)

    object Dashboard : Route(Routes.DASHBOARD)
    object Groups : Route(Routes.GROUPS)
    object Stats : Route(Routes.STATS)
    object Settings : Route(Routes.SETTINGS)

    object Routes {
        const val SPLASH = "splash"
        const val ONBOARDING = "onboarding"
        const val MAIN = "main"
        const val SIGN_IN = "sign-in"
        const val DASHBOARD = "dashboard"
        const val GROUPS = "groups"
        const val STATS = "stats"
        const val SETTINGS = "settings"

    }
}