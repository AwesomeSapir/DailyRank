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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.ui.theme.Spacing

@Composable
fun PlayerCard(modifier: Modifier = Modifier, player: Player) {
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
            PlayerAvater(
                player = player,
                modifier = Modifier.size(64.dp)
            )
            if (player.nickname.isNotBlank()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Spacing.m)
                ) {
                    Text(
                        text = player.nickname,
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
        player = Player(
            uid = "u1",
            nickname = "Sapreme",
        ),
        modifier = Modifier.padding(Spacing.m)
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyPlayerCardPreview() {
    PlayerCard(
        player = Player(
            uid = "u1",
            nickname = "",
        ),
        modifier = Modifier.padding(Spacing.m)
    )
}