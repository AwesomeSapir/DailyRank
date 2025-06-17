package com.sapreme.dailyrank.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.preview.GameResultProvider
import com.sapreme.dailyrank.ui.theme.Spacing
import com.sapreme.dailyrank.ui.theme.sizeM
import com.sapreme.dailyrank.ui.util.iconRes

@Composable
fun GameResultRow(
    type: GameResult.Type,
    gameResults: List<GameResult>
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.m),
            modifier = Modifier.padding(horizontal = Spacing.l)
        ) {
            Image(
                painter = painterResource(id = type.iconRes()),
                contentDescription = "${type.name} icon",
                modifier = Modifier.sizeM()
            )
            Text(
                text = type.toString(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
            )
        }

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
        type = GameResult.Type.WORDLE,
        gameResults = GameResultProvider().values.toList()
    )
}