package com.sapreme.dailyrank.ui.component.dialog

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sapreme.dailyrank.util.validation.FieldState
import com.sapreme.dailyrank.util.validation.ValidationResult
import com.sapreme.dailyrank.viewmodel.CreateGroupViewModel

@Composable
fun CreateGroupDialog(
    viewModel: CreateGroupViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    onError: (Throwable) -> Unit
) {
    val state by viewModel.groupNameField.state

    CreateGroupDialogContent(
        onDismiss = onDismiss,
        state = state,
        onCreate = {
            viewModel.createGroup(
                onSuccess = onSuccess,
                onError = onError
            )
        },
        onGroupNameChange = viewModel::onGroupNameChanged
    )
}

@Composable
fun CreateGroupDialogContent(
    state: FieldState,
    onDismiss: () -> Unit = {},
    onCreate: () -> Unit = {},
    onGroupNameChange: (String) -> Unit = {}
) {
    val focus = LocalFocusManager.current

    AlertDialog(
        modifier = Modifier.windowInsetsPadding(WindowInsets.ime),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                enabled = state.validation is ValidationResult.Valid,
                onClick = {
                    focus.clearFocus()
                    onCreate()
                }
            ) { Text("Create") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Create a new group",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                )
            )
        },
        text = {
            OutlinedTextField(
                value = state.text,
                onValueChange = onGroupNameChange,
                isError = state.validation is ValidationResult.Invalid,
                supportingText = {
                    if (state.validation is ValidationResult.Invalid) {
                        Text(text = (state.validation as ValidationResult.Invalid).error)
                    }
                },
                singleLine = true,
                label = { Text("Group name") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ValidCreateGroupDialogPreview() {
    MaterialTheme {
        CreateGroupDialogContent(
            state = FieldState("Group Name", ValidationResult.Valid),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InvalidCreateGroupDialogPreview() {
    MaterialTheme {
        CreateGroupDialogContent(
            state = FieldState("Group Name", ValidationResult.Invalid("Error message")),
        )
    }
}