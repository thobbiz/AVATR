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
val Geist = FontFamily(
    Font(R.font.geist_bold),
    Font(R.font.geist_medium),
    Font(R.font.geist_regular)
)

// Set of Material typography styles to start with
@RequiresApi(Build.VERSION_CODES.Q)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Geist,
        fontSize = 20.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = Geist,
        fontSize = 12.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = Geist,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = TextStyle(
        fontFamily = Geist,
        fontSize = 30.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Geist,
        fontSize = 26.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Geist,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Geist,
        fontSize = 14.sp
    )
)