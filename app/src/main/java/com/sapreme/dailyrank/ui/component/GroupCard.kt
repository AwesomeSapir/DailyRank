package com.sapreme.dailyrank.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.ui.theme.Spacing

@Composable
fun GroupCard(
    modifier: Modifier = Modifier,
    group: Group,
    playerList: List<Player>,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(Spacing.l),
            verticalArrangement = Arrangement.spacedBy(Spacing.m)
        ) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.m),
                verticalAlignment = Alignment.CenterVertically
            ) {
                playerList.take(5).forEach { player ->
                    PlayerAvater(
                        player = player,
                        modifier = Modifier.size(32.dp)
                    )
                }
                if (playerList.size > 5) {
                    CircleText(
                        text = "+${playerList.size - 5}",
                        size = 32.dp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCardPreview() {
    val sampleGroup = Group(id = "1", name = "Alpha Squad", createdBy = "u1")
    val samplePlayersByGroup = listOf(
        Player(uid = "u1", nickname = "Alice"),
        Player(uid = "u2", nickname = "Bob"),
        Player(uid = "u3", nickname = "Charlie"),
        Player(uid = "u4", nickname = "Andrey"),
        Player(uid = "u5", nickname = "Sapir"),
        Player(uid = "u6", nickname = "Nicole"),
        Player(uid = "u7", nickname = "Yonatan")
    )

    Surface(modifier = Modifier.padding(Spacing.m)) {
        GroupCard(
            group = sampleGroup,
            playerList = samplePlayersByGroup,
            onClick = {}
        )
    }
}
