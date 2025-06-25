package com.sapreme.dailyrank.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleText(
    modifier: Modifier = Modifier,
    text: String,
    size: Dp = 64.dp,
    color: Color = MaterialTheme.colorScheme.primaryContainer
) {

    val fontSize = with(LocalDensity.current) { (size / 2f).toSp() }

    Surface(
        shape = CircleShape,
        color = color,
        modifier = modifier.size(size)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text
                    .take(2)
                    .uppercase(),
                fontSize = fontSize,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmojiCircleTextPreview() {
    CircleText(text = "💀", size = 64.dp, color = Color.DarkGray)
}

@Preview(showBackground = true)
@Composable
fun InitialsCircleTextPreview() {
    CircleText(text = "AB", size = 64.dp, color = MaterialTheme.colorScheme.secondaryContainer)
}

@Preview(showBackground = true)
@Composable
fun SmallEmojiCircleTextPreview() {
    CircleText(text = "\uD83D\uDC80", size = 32.dp, color = Color.DarkGray)
}

@Preview(showBackground = true)
@Composable
fun SmallInitialsCircleTextPreview() {
    CircleText(text = "AB", size = 32.dp, color = MaterialTheme.colorScheme.secondaryContainer)
}