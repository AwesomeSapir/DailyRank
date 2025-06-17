package com.sapreme.dailyrank.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.sapreme.dailyrank.preview.FakeGameResultRepository
import com.sapreme.dailyrank.preview.GameResultProvider
import com.sapreme.dailyrank.ui.component.GameResultCard
import com.sapreme.dailyrank.ui.theme.Spacing
import com.sapreme.dailyrank.ui.viewmodel.GameResultViewModel
import com.sapreme.dailyrank.ui.viewmodel.SubmissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ShareResultScreen(viewModel: GameResultViewModel) {

    val parsed by viewModel.parsed.collectAsState()
    val state by viewModel.submissionState.collectAsState()

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
                ParsedResultView(viewModel = viewModel, result = parsed!!, state = state)
            }
        }
    }
}

@Composable
private fun ParsedResultView(
    viewModel: GameResultViewModel,
    result: GameResult,
    state: SubmissionState
) {
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
            when (state) {
                SubmissionState.Loading -> CircularProgressIndicator()
                SubmissionState.Success -> Text("✅ Submitted!")
                SubmissionState.Error -> Text("❌ Failed to submit.")
                SubmissionState.Idle ->
                    Button(onClick = { viewModel.submit() }) {
                        Text("Submit")
                    }
            }

        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = false
)
@Composable
fun ShareResultScreenPreview(
    @PreviewParameter(GameResultProvider::class) result: GameResult
) {
    val viewModel =
        object : GameResultViewModel(FakeGameResultRepository(predefinedResult = result)) {
        override val parsed: StateFlow<GameResult?> =
            MutableStateFlow(result)
    }
    ShareResultScreen(viewModel = viewModel)
}