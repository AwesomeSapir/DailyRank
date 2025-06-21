package com.sapreme.dailyrank.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleText(
    modifier: Modifier = Modifier,
    text: String,
    size: Dp = 64.dp,
    color: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Surface(
        shape = CircleShape,
        color = color,
        modifier = modifier.size(size)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text
                    .take(2)
                    .uppercase(),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = (size.value / 2).sp,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmojiCircleTextPreview() {
    CircleText(text = "+1", size = 64.dp, color = Color.DarkGray)
}

@Preview(showBackground = true)
@Composable
fun InitialsCircleTextPreview() {
    CircleText(text = "AB", size = 64.dp, color = MaterialTheme.colorScheme.secondaryContainer)
}