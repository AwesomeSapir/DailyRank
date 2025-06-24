package com.sapreme.dailyrank.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sapreme.dailyrank.data.model.FilterKey
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.preview.FakeGameResultRepository
import com.sapreme.dailyrank.ui.component.feature.GameResultCard
import com.sapreme.dailyrank.ui.component.feature.GameResultRow
import com.sapreme.dailyrank.ui.theme.Spacing
import com.sapreme.dailyrank.viewmodel.GameResultViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: GameResultViewModel = hiltViewModel()
) {

    val todayResults by viewModel.getResultsFor(FilterKey.Today).collectAsState()
    val weeklyResults = viewModel.weeklyResultsByType.mapValues { (type, flow) ->
        flow.collectAsState().value
    }
    val today = LocalDate.now()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Surface(modifier = modifier.fillMaxSize()) {
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refreshAll() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Spacing.xl)
            ) {
                DailyGameResults(gameResults = todayResults, today = today)

                WeeklyGameResults(today = today, weeklyResults = weeklyResults)
            }
        }
    }

}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = Spacing.l),
        horizontalArrangement = Arrangement.spacedBy(Spacing.m)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.alignByBaseline()
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.alignByBaseline()
        )
    }
}

@Composable
fun DailyGameResults(gameResults: List<GameResult>, today: LocalDate) {
    Column {
        SectionHeader(
            title = "Today",
            subtitle = today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        )

        if (gameResults.isEmpty()) {
            Text(
                modifier = Modifier.padding(top = Spacing.l, start = Spacing.l, end = Spacing.l),
                text = "No results yet",
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.s),
                horizontalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                gameResults.forEach { result ->
                    GameResultCard(
                        result = result,
                        collapsed = true
                    )
                }
            }
        }
    }
}

@Composable
fun WeeklyGameResults(
    today: LocalDate,
    weeklyResults: Map<GameResult.Type, List<GameResult>>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.m)
    ) {
        SectionHeader(
            title = "Past Week",
            subtitle = "${today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))} - ${
                today.minusDays(
                    6
                ).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            }"
        )

        GameResult.Type.entries.forEach { type ->
            GameResultRow(
                type = type,
                gameResults = weeklyResults.getValue(type)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeeklyGameResultsPreview() {
    WeeklyGameResults(
        LocalDate.now(), weeklyResults = mapOf(
            GameResult.Type.WORDLE to FakeGameResultRepository.wordleResults,
            GameResult.Type.CONNECTIONS to FakeGameResultRepository.connectionsResults,
            GameResult.Type.STRANDS to FakeGameResultRepository.strandsResults,
            GameResult.Type.MINI to FakeGameResultRepository.miniResults,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DailyGameResultsPreview() {
    DailyGameResults(
        today = LocalDate.now(),
        gameResults = listOf(
            FakeGameResultRepository.wordleResults.first(),
            FakeGameResultRepository.connectionsResults.first(),
            FakeGameResultRepository.strandsResults.first(),
            FakeGameResultRepository.miniResults.first(),
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyDailyGameResultsPreview() {
    DailyGameResults(gameResults = emptyList(), today = LocalDate.now())
}