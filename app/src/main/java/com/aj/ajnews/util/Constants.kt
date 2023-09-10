package com.aj.ajnews.util

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.aj.ajnews.R

object Constants {

    val Rubik = FontFamily(
        listOf(
            Font(resId = R.font.rubik_light, weight = FontWeight.Light),
            Font(resId = R.font.rubik_regular, weight = FontWeight.Normal),
            Font(resId = R.font.rubik_medium, weight = FontWeight.Medium),
            Font(resId = R.font.rubik_semi_bold, weight = FontWeight.SemiBold),
            Font(resId = R.font.rubik_bold, weight = FontWeight.Bold),
        )
    )

    val BASE_URL = "https://newsapi.org/"

    val TOP_PICKS_SIZE = 7.0

    val DEFAULT_COUNTRY_CODE = "us"

}