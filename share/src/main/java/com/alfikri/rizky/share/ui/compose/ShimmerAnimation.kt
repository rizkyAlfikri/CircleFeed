package com.alfikri.rizky.share.ui.compose

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version ShimmerAnimation, v 0.1 12/29/2022 8:04 PM by Rizky Alfikri Rachmat
 */
@Composable
fun ShimmerAnimation(
    shimmerContent: (@Composable (Brush) -> Unit),
) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1500, easing = LinearOutSlowInEasing), RepeatMode.Restart
        )
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.4f),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 100f, translateAnim + 100f),
        tileMode = TileMode.Mirror,
    )

    shimmerContent(brush)

}