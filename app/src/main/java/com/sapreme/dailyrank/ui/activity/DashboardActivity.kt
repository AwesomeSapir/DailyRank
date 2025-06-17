package com.sapreme.dailyrank.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sapreme.dailyrank.ui.screen.DashboardScreen
import com.sapreme.dailyrank.ui.theme.DailyRankTheme
import com.sapreme.dailyrank.ui.viewmodel.GameResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {

    private val viewModel: GameResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DailyRankTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DashboardScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

        }
    }
}