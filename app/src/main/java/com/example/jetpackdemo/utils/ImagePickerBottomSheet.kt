package com.example.jetpackdemo.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jetpackdemo.R

@Composable
fun ImagePickerBottomSheet( onDismiss: () -> Unit,
                            onCameraClick: () -> Unit,
                            onGalleryClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Choose Option",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onCameraClick()
                    onDismiss()
                }
                .padding(vertical = 12.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text("Camera", style = MaterialTheme.typography.bodyLarge)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onGalleryClick()
                    onDismiss()
                }
                .padding(vertical = 12.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_gallery), contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text("Gallery", style = MaterialTheme.typography.bodyLarge)
        }
    }
}