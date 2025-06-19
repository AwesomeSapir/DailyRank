package com.sapreme.dailyrank.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sapreme.dailyrank.ui.viewmodel.Destination
import com.sapreme.dailyrank.ui.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onMain: () -> Unit,
    onOnboarding: () -> Unit,
) {
    val destination by viewModel.destination.collectAsState()

    if (destination == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LaunchedEffect(destination) {
            when (destination) {
                Destination.ONBOARDING -> onOnboarding()
                Destination.MAIN -> onMain()
                else -> {}
            }
        }
    }
}