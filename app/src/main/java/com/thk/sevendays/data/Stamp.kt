package com.thk.sevendays.data

import java.time.LocalDate

data class Stamp(
    val challengeId: Int,
    val isChecked: Boolean,
    val date: LocalDate
)

val sampleStampList = listOf(
    Stamp(0, true, LocalDate.now().minusDays(3)),
    Stamp(0, false, LocalDate.now().minusDays(2)),
    Stamp(0, true, LocalDate.now().minusDays(1)),
    Stamp(0, false, LocalDate.now()),
    Stamp(0, false, LocalDate.now().plusDays(1)),
    Stamp(0, false, LocalDate.now().plusDays(2)),
    Stamp(0, false, LocalDate.now().plusDays(3)),
)
