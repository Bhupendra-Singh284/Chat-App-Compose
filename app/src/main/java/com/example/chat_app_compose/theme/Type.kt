package com.example.chat_app_compose.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app_compose.R

// Set of Material typography styles to start with
val poppins = FontFamily(
    fonts = listOf(
        Font(R.font.poppins_black,FontWeight.Black),
        Font(R.font.poppins_extra_bold,FontWeight.ExtraBold),
        Font(R.font.poppins_bold,FontWeight.Bold),
        Font(R.font.poppins_semibold,FontWeight.SemiBold),
        Font(R.font.poppins_medium,FontWeight.Medium),
        Font(R.font.poppins_regular,FontWeight.Normal)
    )
)

val roboto = FontFamily(
    fonts = listOf(
        Font(R.font.roboto_black,FontWeight.Black),
        Font(R.font.roboto_bold,FontWeight.Bold),
        Font(R.font.roboto_medium,FontWeight.Medium),
        Font(R.font.roboto_regular,FontWeight.Normal),
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_thin, FontWeight.Thin)
    )
)

val openSans = FontFamily(
    fonts = listOf(
        Font(R.font.open_sans_extra_bold,FontWeight.ExtraBold),
        Font(R.font.open_sans_bold,FontWeight.Bold),
        Font(R.font.open_sans_semi_bold,FontWeight.SemiBold),
        Font(R.font.open_sans_medium,FontWeight.Medium),
        Font(R.font.open_sans_regular,FontWeight.Normal),
        Font(R.font.open_sans_light, FontWeight.Light)
    )
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 55.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    ),
    labelMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 25.sp
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