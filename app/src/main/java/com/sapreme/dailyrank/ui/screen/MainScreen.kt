package com.sapreme.dailyrank.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sapreme.dailyrank.ui.navigation.Route
import com.sapreme.dailyrank.ui.navigation.mainNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val tabsController = rememberNavController()
    val currentRoute = tabsController.currentBackStackEntryAsState().value?.destination?.route

    val tabs = listOf(
        Screen(
            route = Route.Dashboard.route,
            label = "Dashboard",
            icon = Icons.Default.Home
        ),
        Screen(
            route = Route.Groups.route,
            label = "Groups",
            icon = Icons.Default.People
        )
        // Screen(route = Routes.Stats.route, …), etc.
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "DailyRank",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                )
            })
        },

        bottomBar = {
            NavigationBar {
                tabs.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            tabsController.navigate(screen.route) {
                                popUpTo(tabsController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) }
                    )
                }
            }
        }
    ) { inner ->
        NavHost(
            navController = tabsController,
            startDestination = Route.Dashboard.route,
            modifier = Modifier.padding(inner)
        ) {
            mainNavGraph()
        }
    }

}

private data class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector
)