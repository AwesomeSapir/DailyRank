package com.sapreme.dailyrank.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.ui.component.feature.PlayerCard
import com.sapreme.dailyrank.ui.theme.GameColor
import com.sapreme.dailyrank.ui.theme.Spacing
import com.sapreme.dailyrank.ui.theme.sizeM
import com.sapreme.dailyrank.ui.theme.sizeXL
import com.sapreme.dailyrank.viewmodel.OnboardingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val ui by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.done.collectLatest { finished ->
            if (finished) {
                onFinished()
            }
        }
    }

    OnboardingContent(
        ui = ui,
        onNameChange = viewModel::onNicknameChange,
        onContinue = viewModel::savePlayer
    )
}

@Composable
fun OnboardingContent(
    modifier: Modifier = Modifier,
    ui: OnboardingViewModel.UiState,
    onNameChange: (String) -> Unit,
    onContinue: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.l),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Hello!",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                ),
                color = GameColor.Mini.Blue
            )
            Spacer(Modifier.sizeXL())

            PlayerCard(
                modifier = Modifier,
                player = Player(
                    uid = ui.uid,
                    nickname = ui.nickname
                ),
            )

            Spacer(Modifier.sizeXL())

            PlayerForm(
                ui = ui,
                onNameChange = onNameChange,
            )

            Spacer(Modifier.sizeXL())
            if (ui.continueEnabled || ui.isSaving) {
                Button(
                    onClick = onContinue,
                    enabled = ui.continueEnabled,
                    modifier = Modifier
                ) {
                    if (ui.isSaving) CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.sizeXL()
                    )
                    else Text("Continue")
                }
            }
        }
    }
}

@Composable
fun PlayerForm(ui: OnboardingViewModel.UiState, onNameChange: (String) -> Unit) {
    Column {
        Text(
            "Please choose a nickname",
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            "Your nickname will be displayed to other players.",
            style = MaterialTheme.typography.labelSmall,
        )
        Spacer(Modifier.sizeM())
        OutlinedTextField(
            value = ui.nickname,
            onValueChange = onNameChange,
            label = { Text("Nickname") },
            singleLine = true,
            isError = ui.error != null,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
        )
        ui.error?.let {
            Spacer(Modifier.sizeM())
            Text(
                it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingContentSavingPreview() {
    val fakeState =
        OnboardingViewModel.UiState(nickname = "Sapir", isSaving = true)
    OnboardingContent(
        ui = fakeState,
        onNameChange = {},
        onContinue = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingContentEmptyPreview() {
    val fakeState =
        OnboardingViewModel.UiState(nickname = "", isSaving = false)
    OnboardingContent(
        ui = fakeState,
        onNameChange = {},
        onContinue = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingContentDefaultPreview() {
    val fakeState =
        OnboardingViewModel.UiState(nickname = "Sapir", isSaving = false)
    OnboardingContent(
        ui = fakeState,
        onNameChange = {},
        onContinue = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingContentErrorPreview() {
    val fakeState =
        OnboardingViewModel.UiState(nickname = "Sapir!", isSaving = false, error = "Error")
    OnboardingContent(
        ui = fakeState,
        onNameChange = {},
        onContinue = {}
    )
}