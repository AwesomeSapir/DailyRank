package com.sapreme.dailyrank.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sapreme.dailyrank.R
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.ui.util.avatarUrl

@Composable
fun PlayerAvater(
    modifier: Modifier = Modifier,
    player: Player
) {
    AsyncImage(
        model = player.avatarUrl(),
        contentDescription = "Avatar for ${player.nickname}",
        placeholder = painterResource(R.drawable.ic_avatar_placeholder),
        error = painterResource(R.drawable.ic_avatar_placeholder),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PlayerAvaterPreview() {
    PlayerAvater(
        modifier = Modifier.size(64.dp),
        player = Player(
            uid = "u1",
            nickname = "Sapreme",
        )
    )
}