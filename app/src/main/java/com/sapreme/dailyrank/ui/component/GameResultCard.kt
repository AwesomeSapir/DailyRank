package com.sapreme.dailyrank.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.ui.theme.colorPalette
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun GameResultCard(result: GameResult) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = result.type.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = result.type.colorPalette().title,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    "#${"%,d".format(result.puzzleId)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignByBaseline()
                )
            }

            BooleanIcon(
                result.succeeded,
                positiveString = "Completed",
                negativeString = "Failed",
                positiveColor = result.type.colorPalette().positive,
                negativeColor = result.type.colorPalette().negative,
                textStyle = MaterialTheme.typography.labelMedium,
            )

            Spacer(Modifier.size(8.dp))
            when (result) {
                is GameResult.WordleResult -> WordleResultView(result)
                is GameResult.ConnectionsResult -> ConnectionsResultView(result)
                is GameResult.MiniResult -> TODO()
                is GameResult.StrandsResult -> StrandsResultView(result)
            }
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Submitted on ${result.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))}",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun WordleResultView(result: GameResult.WordleResult) {
    Text(
        text = "In ${result.attempts} tries",
        style = MaterialTheme.typography.labelMedium,
    )
}

@Composable
fun ConnectionsResultView(result: GameResult.ConnectionsResult) {

    Text(
        text = "In ${result.attempts} tries",
        style = MaterialTheme.typography.labelMedium,
    )
    Spacer(Modifier.size(4.dp))
    Text(
        text = "Grouped ${result.groupings} categories",
        style = MaterialTheme.typography.labelMedium
    )
    Spacer(Modifier.size(4.dp))
    Text(
        text = "With ${result.mistakes} mistakes",
        style = MaterialTheme.typography.labelMedium,
    )
}

@Composable
fun StrandsResultView(result: GameResult.StrandsResult) {
    Text(
        text = "Found ${result.words} words",
        style = MaterialTheme.typography.labelMedium,
    )
    Spacer(Modifier.size(4.dp))
    Text(
        text = "Using ${result.hints} hints",
        style = MaterialTheme.typography.labelMedium
    )
    if (result.doubleHints > 0) {
        Spacer(Modifier.size(4.dp))
        Text(
            text = "And ${result.doubleHints} mistakes",
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWordleResultCard() {
    GameResultCard(
        result = GameResult.WordleResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 1023,
            attempts = 4,
            succeeded = true
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewWordleFailedResultCard() {
    GameResultCard(
        result = GameResult.WordleResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 1023,
            attempts = 6,
            succeeded = false
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectionsResultCard() {
    GameResultCard(
        result = GameResult.ConnectionsResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 715,
            attempts = 6,
            groupings = 4,
            mistakes = 2,
            succeeded = true
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectionsFailedResultCard() {
    GameResultCard(
        result = GameResult.ConnectionsResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 715,
            attempts = 6,
            groupings = 2,
            mistakes = 4,
            succeeded = false
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewStrandsResultCard() {
    GameResultCard(
        result = GameResult.StrandsResult(
            date = LocalDate.of(2025, 6, 10),
            title = "At Long Last",
            puzzleId = 449,
            words = 6,
            hints = 2,
            doubleHints = 1
        )
    )
}


