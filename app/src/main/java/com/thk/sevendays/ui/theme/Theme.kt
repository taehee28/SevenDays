package com.thk.sevendays.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Red100,
    primaryVariant = Red300,
    secondary = Blue300
)

private val LightColorPalette = lightColors(
    primary = Red200,
    primaryVariant = Red300,
    secondary = Blue300

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SevenDaysAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val sevenDaysColors = if (darkTheme) {
        SevenDaysDarkPalette
    } else {
        SevenDaysLightPalette
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = colors.background)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = {
            CompositionLocalProvider(
                LocalSevenDaysColors provides sevenDaysColors,
                content = content
            )
        }
    )
}