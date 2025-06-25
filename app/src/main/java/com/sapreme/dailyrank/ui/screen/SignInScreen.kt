package com.sapreme.dailyrank.ui.screen

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.sapreme.dailyrank.R
import com.sapreme.dailyrank.data.auth.AuthResult
import com.sapreme.dailyrank.data.auth.AuthStatus
import com.sapreme.dailyrank.ui.theme.Spacing
import com.sapreme.dailyrank.viewmodel.SignInViewModel
import com.sapreme.dailyrank.viewmodel.SignInViewModel.UiState
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignedIn: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val activity = context as ComponentActivity
    val credentialManager = remember { CredentialManager.create(context) }
    val scope = rememberCoroutineScope()

    val state by viewModel.uiState.collectAsState()
    val authStatus by viewModel.authStatus.collectAsState()

    LaunchedEffect(authStatus) {
        (authStatus as? AuthStatus.Authenticated)?.let { onSignedIn() }
    }

    SignInScreenContent(
        loading = state.authResult is AuthResult.Loading,
        error = (state.authResult as? AuthResult.Error)?.message,
        modifier = modifier.fillMaxSize(),
        onGoogleSignIn = {
            scope.launch {
                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(
                        GetGoogleIdOption.Builder()
                            .setFilterByAuthorizedAccounts(false)
                            .setServerClientId(context.getString(R.string.default_web_client_id))
                            .build()
                    )
                    .build()
                try {
                    val result = credentialManager.getCredential(activity, request)
                    val idToken = (result.credential as? CustomCredential)?.takeIf {
                        it.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                    }?.let {
                        GoogleIdTokenCredential.createFrom(it.data).idToken
                    }

                    viewModel.signInWithGoogle(idToken ?: "")
                } catch (e: Exception) {
                    viewModel.signInWithGoogle("")
                }
            }
        },
        onAnonymousSignIn = viewModel::signInAsGuest,
    )
}

@Composable
private fun SignInScreenContent(
    modifier: Modifier = Modifier,
    loading: Boolean,
    error: String?,
    onGoogleSignIn: () -> Unit = {},
    onAnonymousSignIn: () -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /* --- Google Sign-In button --- */
            Button(
                onClick = onGoogleSignIn,
                enabled = !loading
            ) { Text("Continue with Google") }

            Spacer(Modifier.height(16.dp))

            /* --- Anonymous fallback --- */
            OutlinedButton(
                onClick = onAnonymousSignIn,
                enabled = !loading
            ) { Text("Continue anonymously") }

            if (loading) {
                Spacer(Modifier.height(32.dp))
                CircularProgressIndicator()
            }

            error?. let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenContentPreview() {
    SignInScreenContent(
        loading = false,
        error = null,
        onAnonymousSignIn = {},
        onGoogleSignIn = {}
    )
}
