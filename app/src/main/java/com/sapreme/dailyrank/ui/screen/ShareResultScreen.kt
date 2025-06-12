package com.sapreme.dailyrank.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.repository.GameResultRepository
import com.sapreme.dailyrank.ui.component.GameResultCard
import com.sapreme.dailyrank.ui.preview.GameResultProvider
import com.sapreme.dailyrank.ui.theme.Spacing
import com.sapreme.dailyrank.ui.viewmodel.GameResultViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.l, vertical = Spacing.l),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.l)
        ) {
            GameResultCard(
                result = result,
                modifier = Modifier.wrapContentSize()
            )
            Button(
                onClick = { },
            ) {
                Text(text = "Submit")
            }
        }
    }
}



@Preview(
    showBackground = true,
    showSystemUi = false
)
@Composable
fun Preview(
    @PreviewParameter(GameResultProvider::class) result: GameResult
) {
    val viewModel = object : GameResultViewModel(GameResultRepository()) {
        override val parsed: StateFlow<GameResult?> =
            MutableStateFlow(result)
    }
    ShareResultScreen(viewModel = viewModel)
}