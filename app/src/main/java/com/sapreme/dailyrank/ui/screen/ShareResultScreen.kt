package com.sapreme.dailyrank.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.ui.viewmodel.GameResultViewModel
import java.time.LocalDate

@Composable
fun ShareResultScreen(viewModel: GameResultViewModel) {

    val parsed by viewModel.parsed.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            parsed == null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Loading...")
                }
            }

            else -> {
                ParsedResultView(result = parsed!!)
            }
        }
    }
}

@Composable
private fun ParsedResultView(result: GameResult) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text("Game: ${result::class.simpleName}", style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun ShareResultPreview() {
    ParsedResultView(
        result = GameResult.WordleResult(
            puzzleId = 123,
            date = LocalDate.now(),
            succeeded = true,
            attempts = 4
        )
    )
}