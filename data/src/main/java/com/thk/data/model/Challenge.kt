package com.thk.data.model

import java.time.LocalDate
import java.util.*

data class Challenge(
    val challengeId : Int = 0,
    val title: String,
    val startDate: LocalDate
)
