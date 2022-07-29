package com.thk.data.model

import java.time.LocalDate
import java.util.*

data class Challenge(
    val challengeId : Int = 0,
    val title: String,
    val startDate: LocalDate
)

val sampleChallengeList = listOf(
    Challenge(title = "하루 한번 산책", startDate = LocalDate.now()),
    Challenge(title = "책 읽기", startDate = LocalDate.now().minusDays(3)),
    Challenge(title = "하루에 물 2리터 마시기", startDate = LocalDate.now().minusDays(9))
)