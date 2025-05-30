package com.petrocini.progressodecarga.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavController,
    user: FirebaseUser?,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.systemBarsPadding(),
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                user?.photoUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Foto",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = user?.displayName ?: "Usuário",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text("Sair") },
                    selected = false,
                    onClick = onLogout
                )
                NavigationDrawerItem(
                    label = { Text("Desvincular conta") },
                    selected = false,
                    onClick = onDeleteAccount
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.systemBarsPadding(),
            topBar = {
                TopAppBar(
                    title = { Text("Progressão de Carga") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            content = content
        )
    }
}
