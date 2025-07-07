package com.example.jetpackdemo.presentation.home.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackdemo.R
import com.example.jetpackdemo.domain.model.User
import com.example.jetpackdemo.presentation.profile.viewmodels.ProfileViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.utils.LogoutDialog
import com.example.jetpackdemo.utils.UserPreferences

@Composable
fun SettingsScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    email: String,
    onLogoutConfirm: () -> Unit,
    onEditProfileClick: () -> Unit,
    onChangePasswordClick: (String) -> Unit
) {
    val ctx = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var profile by remember { mutableStateOf<User?>(null) } // Replace `ProfileModel` with your actual data class name
    val prefs = remember { UserPreferences(ctx) }

//    var profileFlow = remember { UserPreferences(ctx).getProfile() }
//    // collect → Compose State
//    val profile by profileFlow.collectAsState(initial = null)

    var showLogout by remember { mutableStateOf(false) }/* --- local state for the Push‑Notification switch --- */
    var pushEnabled by remember { mutableStateOf(true) }
    // Runs EVERY TIME screen becomes active again

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            prefs.getProfile().collect { profile = it }
        }
    }




    JetpackDemoTheme {
        Scaffold() { innerPad ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPad)
                    .padding(horizontal = 20.dp)
            ) {
                /* ---------- profile card ---------- */
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /* avatar */
                        AsyncImage(
                            model = ImageRequest.Builder(ctx)
                                .data(profile?.imageUrl ?: R.drawable.avatar)
                                .placeholder(R.drawable.avatar)
                                .error(R.drawable.avatar) // Represents an error or fallback state
                                .crossfade(true).build(),
                            contentDescription = "avatar",
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = .1f)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(12.dp))

                        Column(Modifier.weight(1f)) {
                            Text("Welcome", style = MaterialTheme.typography.labelMedium)
                            Text(
                                "Mr. ${profile?.username}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        IconButton(onClick = {
                            showLogout = true
                        }) {
                            Icon(Icons.Filled.ExitToApp, contentDescription = "share")
                        }
                    }
                    HorizontalDivider(Modifier.padding(vertical = 16.dp))
                }/* ---------- normal list items ---------- */
                item { SettingsRow(Icons.Default.Edit, "Edit Profile", { onEditProfileClick() }) }
                item {
                    SettingsRow(
                        Icons.Filled.Lock,
                        "Change Password",
                        { onChangePasswordClick(profile?.email ?: "") })
                }
                item {
                    SettingsRow(
                        Icons.Outlined.Info,
                        "FAQs",
                        {

                        })
                }/* ---------- switch row ---------- */
                item {
                    SettingsRow(
                        leading = { Icon(Icons.Filled.Notifications, null) },
                        title = "Push Notification",
                        trailing = {
                            Switch(
                                checked = pushEnabled,
                                onCheckedChange = { pushEnabled = it },
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = Color(0xFF21CBF3)
                                )
                            )
                        })
                }/* ---------- footer card ---------- */
                item {
                    Spacer(Modifier.height(24.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8F9)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "If you have any other query you\ncan reach out to us.",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "WhatsApp Us", style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline,
                                    color = MaterialTheme.colorScheme.primary
                                ), modifier = Modifier.clickable(onClick = { })
                            )
                        }
                    }
                    Spacer(Modifier.height(32.dp))

                }
            }

            if (showLogout) {
                LogoutDialog(
                    icon = Icons.Filled.ExitToApp,
                    title = stringResource(R.string.logout),
                    message = stringResource(R.string.sure_logout),
                    yesText = stringResource(R.string.yes),
                    noText = stringResource(R.string.no),
                    onClose = {
                        showLogout = false
                    },
                    onConfirm = {
                        showLogout = false
                        onLogoutConfirm()
                    })
            }
        }
    }
}