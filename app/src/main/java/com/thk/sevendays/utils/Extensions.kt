package com.thk.sevendays.utils

import java.time.LocalDate

public val <T> List<T>.firstIndex: Int
    get() = if (this.isNotEmpty()) 0 else -1

fun LocalDate.isToday() = this == LocalDate.now()