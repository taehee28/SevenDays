package com.thk.sevendays.utils

import androidx.navigation.NavController
import com.thk.sevendays.SevenDaysScreen
import java.time.LocalDate
import java.time.temporal.ChronoUnit

public val <T> List<T>.firstIndex: Int
    get() = if (this.isNotEmpty()) 0 else -1

fun LocalDate.isToday() = this == LocalDate.now()

fun LocalDate.challengingDaysFrom(startDate: LocalDate): Int {
    return ChronoUnit.DAYS.between(startDate, this).toInt() + 1
}

fun NavController.navigateToDetail(id: Int) {
    navigate("${SevenDaysScreen.Detail.name}/$id")
}