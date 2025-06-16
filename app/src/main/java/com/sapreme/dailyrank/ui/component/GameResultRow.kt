package com.sapreme.dailyrank.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.preview.GameResultProvider
import com.sapreme.dailyrank.ui.theme.Spacing

@Composable
fun GameResultRow(
    title: String,
    gameResults: List<GameResult>
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(horizontal = Spacing.l)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = Spacing.s),
            horizontalArrangement = Arrangement.spacedBy(Spacing.s)
        ) {
            items(gameResults.size) { index ->
                GameResultCard(result = gameResults[index])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    GameResultRow(
        title = "Wordle",
        gameResults = GameResultProvider().values.toList()
    )
}