package com.sapreme.dailyrank.ui.component.feature

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.preview.GameResultProvider
import com.sapreme.dailyrank.ui.theme.GameColor
import com.sapreme.dailyrank.ui.theme.Spacing
import com.sapreme.dailyrank.ui.theme.Typography
import com.sapreme.dailyrank.ui.theme.colorPalette
import com.sapreme.dailyrank.ui.theme.sizeL
import com.sapreme.dailyrank.ui.theme.sizeM
import com.sapreme.dailyrank.util.puzzleLabel
import java.time.format.DateTimeFormatter

@Composable
fun GameResultCard(modifier: Modifier = Modifier, result: GameResult, collapsed: Boolean = false) {

    val palette = remember(result.type) { result.type.colorPalette() }

    val formattedDate = remember(result.submitDate) {
        result.submitDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
    }

    Card(
        modifier = modifier.padding(Spacing.m),
        border = BorderStroke(
            width = 4.dp,
            color = if (result.succeeded) palette.title else Color.DarkGray,
        )
    ) {
        Column(modifier = Modifier.padding(Spacing.l)) {
            Text(
                text = result.type.toString(),
                style = Typography.titleLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Black,
                ),
                color = palette.title,
            )
            GameResultHeader(
                succeeded = result.succeeded,
                label = result.puzzleLabel()
            )
            if (!collapsed) {
                Spacer(Modifier.sizeM())
                when (result) {
                    is GameResult.WordleResult -> WordleResultContent(result)
                    is GameResult.ConnectionsResult -> ConnectionsResultContent(result)
                    is GameResult.MiniResult -> MiniResultContent(result)
                    is GameResult.StrandsResult -> StrandsResultContent(result)
                }
                Spacer(Modifier.sizeM())
                Text(
                    text = "Submitted on $formattedDate",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                )
            }
        }
    }
}

@Composable
fun GameResultHeader(succeeded: Boolean, label: String) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.m)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            ),
        )
        Icon(
            imageVector = if (succeeded) Icons.Default.CheckCircle else Icons.Default.Cancel,
            contentDescription = if (succeeded) "Completed" else "Failed",
            tint = if (succeeded) GameColor.Mini.Blue else GameColor.Misc.Red,
            modifier = Modifier.sizeL()
        )
    }
}

@Composable
fun WordleResultContent(result: GameResult.WordleResult) {
    val highlightColor = if (result.succeeded) GameColor.Wordle.Green else GameColor.Wordle.Yellow

    Text(
        text = buildAnnotatedString {
            append("In ")
            withStyle(SpanStyle(color = highlightColor)) {
                append("${result.attempts}")
            }
            append(" tries")
        },
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold
        )
    )
}

@Composable
fun ConnectionsResultContent(result: GameResult.ConnectionsResult) {

    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.s)
    ) {
        Text(
            text = buildAnnotatedString {
                append("In ")
                withStyle(SpanStyle(color = GameColor.Connections.Purple)) {
                    append("${result.attempts}")
                }
                append(" tries")
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = buildAnnotatedString {
                append("Grouped ")
                withStyle(SpanStyle(color = GameColor.Connections.Blue)) {
                    append("${result.groupings}")
                }
                append(" categories")
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = buildAnnotatedString {
                append("With ")
                withStyle(SpanStyle(color = GameColor.Misc.Red)) {
                    append("${result.mistakes}")
                }
                append(" mistakes")
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun StrandsResultContent(result: GameResult.StrandsResult) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.s)
    ) {
        Text(
            text = buildAnnotatedString {
                append("Found ")
                withStyle(SpanStyle(color = GameColor.Strands.Cyan)) {
                    append("${result.words}")
                }
                append(" words")
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = buildAnnotatedString {
                append("Used ")
                withStyle(SpanStyle(color = GameColor.Strands.Yellow)) {
                    append("${result.hints}")
                }
                append(" hints")
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = buildAnnotatedString {
                append("And ")
                withStyle(SpanStyle(color = GameColor.Strands.DarkYellow)) {
                    append("${result.doubleHints}")
                }
                append(" reveals")
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )
        )
    }

}

@Composable
fun MiniResultContent(result: GameResult.MiniResult) {
    Text(
        text = buildAnnotatedString {
            append("Completed in ")
            withStyle(SpanStyle(color = GameColor.Mini.Blue, fontWeight = FontWeight.Bold)) {
                append(result.duration.toComponents { m, s, _ -> "${m}m ${s}s" })
            }
        },
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewResultCard(
    @PreviewParameter(GameResultProvider::class)
    result: GameResult
) {
    GameResultCard(result = result)
}

@Preview(showBackground = true)
@Composable
fun PreviewCollapsedResultCard(
    @PreviewParameter(GameResultProvider::class)
    result: GameResult
) {
    GameResultCard(result = result, collapsed = true)
}
