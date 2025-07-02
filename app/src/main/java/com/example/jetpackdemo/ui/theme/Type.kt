    package com.example.jetpackdemo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jetpackdemo.R

    // Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
    /** Montserrat family bundled in res/font/ */
     val Montserrat = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_medium,  FontWeight.Medium),
        Font(R.font.montserrat_bold,    FontWeight.Bold),
        Font(R.font.montserrat_semibold,    FontWeight.SemiBold)
    )
    /** App‑wide typography using Montserrat for every role */
    val AppTypography = Typography(
        displayLarge  = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Bold,   fontSize = 57.sp),
        displayMedium = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Bold,   fontSize = 45.sp),
        displaySmall  = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Bold,   fontSize = 36.sp),
        headlineLarge = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Bold,   fontSize = 32.sp),
        headlineMedium= TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 28.sp),
        titleLarge    = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.SemiBold, fontSize = 22.sp),
        titleMedium    = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
        bodyLarge     = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        bodyMedium    = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Normal, fontSize = 14.sp),
        labelSmall    = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 11.sp),
    )