package com.sapreme.dailyrank.ui.component.atom

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun BooleanIcon(
    value: Boolean,
    positiveString: String,
    negativeString: String,
    positiveColor: Color = MaterialTheme.colorScheme.onSurface,
    negativeColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (value) Icons.Default.CheckCircle else Icons.Default.Cancel,
            contentDescription = if (value) positiveString else negativeString,
            tint = if (value) positiveColor else negativeColor
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(if (value) positiveString else negativeString, style = textStyle)
    }
}