package com.example.jetpackdemo.utils

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingOverlay(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    overlayColor: Color = Color.Black.copy(alpha = 0.35f),
    indicatorColor: Color = Color(0xFF2196F3) // You can customize
) {
    if (isLoading) {
        val infiniteTransition = rememberInfiniteTransition(label = "loading-animation")
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 700),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )

        Box(
            modifier = modifier
                .fillMaxSize().background(overlayColor),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = indicatorColor,
                    modifier = Modifier
                        .size(48.dp)
                        .scale(scale)
                )
            }
        }
    }
}
