package com.thk.sevendays.data

import java.time.LocalDate
import java.util.*

data class Challenge(
    val title: String,
    val startDate: LocalDate,
    val id: UUID = UUID.randomUUID()
)
