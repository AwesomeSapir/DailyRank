package com.sapreme.dailyrank.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.sapreme.dailyrank.R
import com.sapreme.dailyrank.data.auth.AuthResult
import com.sapreme.dailyrank.data.auth.AuthStatus
import com.sapreme.dailyrank.viewmodel.SignInViewModel
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
