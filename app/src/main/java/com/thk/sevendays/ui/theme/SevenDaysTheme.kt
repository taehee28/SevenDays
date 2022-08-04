package com.thk.sevendays.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object SevenDaysTheme {
    val colors: SevenDaysColors
        @Composable
        @ReadOnlyComposable
        get() = LocalSevenDaysColors.current
}