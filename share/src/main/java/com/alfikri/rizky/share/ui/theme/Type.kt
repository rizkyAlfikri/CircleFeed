package com.alfikri.rizky.share.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alfikri.rizky.share.R

private val circularStdBold = FontFamily(Font(R.font.circular_std_bold))
private val circularStdBook = FontFamily(Font(R.font.circular_std_book))

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = circularStdBold,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
    ),
    h2 = TextStyle(
        fontFamily = circularStdBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = (-0.1).sp,
    ),
    h3 = TextStyle(
        fontFamily = circularStdBold,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = (0).sp,
    ),
    h4 = TextStyle(
        fontFamily = circularStdBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = (-0.1).sp,
    ),
    body1 = TextStyle(
        fontFamily = circularStdBook,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = (0.4).sp,
    ),
    body2 = TextStyle(
        fontFamily = circularStdBook,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = (-0.2).sp,
    ),
    caption = TextStyle(
        fontFamily = circularStdBook,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = (2).sp,
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
