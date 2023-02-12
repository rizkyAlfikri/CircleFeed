package com.alfikri.rizky.share.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alfikri.rizky.share.R
import com.alfikri.rizky.share.ui.theme.Grey6

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version Common, v 0.1 12/26/2022 6:59 PM by Rizky Alfikri Rachmat
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = CircleShape, backgroundColor = Grey6) {
        LoadingCircle()
    }
}

@Composable
fun LoadingCircle() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_loading))
    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier
            .size(56.dp)
            .padding(12.dp)
    )
}