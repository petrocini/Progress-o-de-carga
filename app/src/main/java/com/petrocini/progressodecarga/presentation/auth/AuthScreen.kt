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
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.petrocini.progressodecarga.presentation.navigation.Screen
import kotlinx.coroutines.tasks.await

@Composable
fun AuthScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = Firebase.auth
    val oneTapClient = remember { Identity.getSignInClient(context) }
    var loading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            loading = true
            Firebase.auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    loading = false
                    if (task.isSuccessful) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Auth.route) { inclusive = true }
                        }
                    }
                }
        }
    }

    LaunchedEffect(Unit) {
        val request = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("SEU_CLIENT_ID_DO_FIREBASE_WEB")
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        val result = runCatching { oneTapClient.beginSignIn(request).await() }
        result.onSuccess { launcher.launch(IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()) }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            Text("Iniciando login com Google...")
        }
    }
}
