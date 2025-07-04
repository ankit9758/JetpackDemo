package com.example.jetpackdemo.presentation.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/* --- reusable list row --- */

@Composable
fun SettingsRow(
    icon: ImageVector? = null,
    title: String,
    onClick: () -> Unit = {},
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = {
        Icon(Icons.Filled.KeyboardArrowRight, null)
    }
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leading != null) {
            leading()
        } else if (icon != null) {
            Icon(icon, contentDescription = null)
        }
        Spacer(Modifier.width(16.dp))
        Text(title, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        trailing?.invoke()
    }
    HorizontalDivider()

}