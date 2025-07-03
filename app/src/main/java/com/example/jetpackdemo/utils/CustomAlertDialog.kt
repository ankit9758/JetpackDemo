package com.example.jetpackdemo.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomAlertDialog(
    icon: ImageVector,
    title: String,
    message: String,
    isSuccess: Boolean = true,
    confirmText: String = "OK",
    onClose: () -> Unit,
    onConfirm: () -> Unit = onClose
){
    Dialog(

        onDismissRequest = { /* non‑dismissible unless onClose is called */ },
        properties = DialogProperties(
            usePlatformDefaultWidth = false, // 🔥 disables default padding
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp) // This keeps it away from screen edge
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Box {
                    /* Main content centred */
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSuccess) Color(0xFF4CAF50) else Color(0xFFF44336),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isSuccess) Color(0xFF4CAF50) else Color(0xFFF44336),
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = message,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                        )

                        if(!isSuccess){
                            Spacer(Modifier.height(20.dp))
                            Button(onClick = onConfirm) {
                                Text(confirmText,style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                            }
                        }

                    }
                    /* ✕ icon pinned to the very corner */
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .align(Alignment.TopEnd)

                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }

    }
}