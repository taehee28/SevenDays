package com.thk.sevendays.utils

import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * 시작일로부터 며칠째 도전중인지
 */
fun LocalDate.challengingDaysFrom(startDate: LocalDate): Int {
    return ChronoUnit.DAYS.between(startDate, this).toInt() + 1
}