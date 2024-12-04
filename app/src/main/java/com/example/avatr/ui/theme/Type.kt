package com.example.avatr.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.avatr.R


@RequiresApi(Build.VERSION_CODES.Q)
val CircluarStd = FontFamily(
    Font(R.font.circularstd_medium),
    Font(R.font.circularstd_bold)
)

// Set of Material typography styles to start with
@RequiresApi(Build.VERSION_CODES.Q)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = CircluarStd,
        fontSize = 17.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = CircluarStd,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = CircluarStd,
        fontSize = 16.sp
    ),
    displayLarge = TextStyle(
        fontFamily = CircluarStd,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = TextStyle(
        fontFamily = CircluarStd,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = CircluarStd,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = CircluarStd,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = CircluarStd,
        fontSize = 20.sp
    ),

    labelMedium = TextStyle(
        fontFamily = CircluarStd,
        fontSize = 17.sp,
        fontWeight = FontWeight.Thin
    )
)