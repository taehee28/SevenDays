package com.thk.sevendays.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


class SevenDaysColors(
    val inChallengeFontColor: Color,
    val inChallengeBackground: Color,
    val finishedFontColor: Color,
    val finishedBackground: Color
)

fun sevenDaysLightColors(
    inChallengeFontColor: Color = Red300,
    inChallengeBackground: Color = Color.White,
    finishedFontColor: Color = Indigo400,
    finishedBackground: Color = Color(0xFFFAFBFF)
): SevenDaysColors = SevenDaysColors(
    inChallengeFontColor,
    inChallengeBackground,
    finishedFontColor,
    finishedBackground
)

fun sevenDaysDarkColors(
    inChallengeFontColor: Color = Red200,
    inChallengeBackground: Color = Color(0xFF121212),
    finishedFontColor: Color = Indigo300,
    finishedBackground: Color = Color(0xFF121213)
): SevenDaysColors = SevenDaysColors(
    inChallengeFontColor,
    inChallengeBackground,
    finishedFontColor,
    finishedBackground
)

val SevenDaysLightPalette = sevenDaysLightColors()
val SevenDaysDarkPalette = sevenDaysDarkColors()

internal val LocalSevenDaysColors = staticCompositionLocalOf { sevenDaysLightColors() }

