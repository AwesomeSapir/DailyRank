package com.sapreme.dailyrank.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sapreme.dailyrank.R
import com.sapreme.dailyrank.ui.theme.Spacing

@Composable
fun PlayerCard(modifier: Modifier = Modifier, avatarUrl: String, name: String) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(Spacing.m),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.l)
        ) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = "Avatar for $name",
                placeholder = painterResource(R.drawable.ic_avatar_placeholder),
                error = painterResource(R.drawable.ic_avatar_placeholder),
                modifier = Modifier.size(64.dp)
            )
            if (name.isNotBlank()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Spacing.m)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerCardPreview() {
    PlayerCard(
        avatarUrl = "",
        name = "Sapreme",
        modifier = Modifier.padding(Spacing.m)
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyPlayerCardPreview() {
    PlayerCard(
        avatarUrl = "",
        name = "",
        modifier = Modifier.padding(Spacing.m)
    )
}