package com.petrocini.progressodecarga.presentation.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.petrocini.progressodecarga.BuildConfig
import com.petrocini.progressodecarga.presentation.navigation.Screen
import kotlinx.coroutines.tasks.await

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val oneTapClient = remember { Identity.getSignInClient(context) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken
            viewModel.signInWithGoogle(idToken) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Auth.route) { inclusive = true }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkIfUserLoggedIn {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Auth.route) { inclusive = true }
            }
        }

        val request = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.FIREBASE_WEB_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        val result = runCatching { oneTapClient.beginSignIn(request).await() }
        result.onSuccess {
            launcher.launch(IntentSenderRequest.Builder(it.pendingIntent.intentSender).build())
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.loading) {
            CircularProgressIndicator()
        } else {
            Text("Iniciando login com Google...")
        }
    }
}