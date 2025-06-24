package com.sapreme.dailyrank.ui.component.dialog

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.ui.theme.Spacing
import com.sapreme.dailyrank.ui.theme.sizeM
import com.sapreme.dailyrank.viewmodel.InviteViewModel
import java.time.LocalDate

@Composable
fun InviteDialog(
    viewModel: InviteViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    group: Group,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(group) { viewModel.load(group.id) }

    state?.let { data ->
        InviteDialogContent(
            group = group,
            state = data,
            onDismiss = onDismiss
        )
    }
}

@Composable
fun InviteDialogContent(
    group: Group,
    state: InviteViewModel.UiState,
    onDismiss: () -> Unit,
) = AlertDialog(
    onDismissRequest = onDismiss,
    title = {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Invite code",
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
            )
        )
    },
    text = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.m)
        ) {
            Text(
                group.name, style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Image(state.qr, null, Modifier.fillMaxWidth())
            SelectionContainer {
                Text(
                    state.code, style = MaterialTheme.typography.displayMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        }
    },
    confirmButton = {
        val context = LocalContext.current
        OutlinedButton(
            onClick = {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Join my DailyRank group with: ${state.code}")
                    context.startActivity(Intent.createChooser(this, "Share invite code"))
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share"
            )
            Spacer(Modifier.sizeM())
            Text("Share")
        }
    },
    dismissButton = {
        TextButton(onClick = onDismiss) { Text("Close") }
    }
)

@Preview(showBackground = true)
@Composable
fun InviteDialogPreview() {
    InviteDialogContent(
        group = Group(
            id = "",
            name = "Alpha Squad",
            createdBy = "u1",
            createdAt = LocalDate.now()
        ),
        state = InviteViewModel.UiState(
            code = "ABC012",
            qr = ImageBitmap(1, 1)
        ),
        onDismiss = {}
    )
}