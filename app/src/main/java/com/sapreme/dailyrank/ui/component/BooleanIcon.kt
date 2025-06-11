package com.sapreme.dailyrank.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
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