package com.thk.data.model

import java.time.LocalDate
import java.util.*

data class Challenge(
    val challengeId : Int = 0,
    val title: String,
    val startDate: LocalDate = LocalDate.now()
)

val sampleChallengeList = listOf(
    Challenge(challengeId = 1, title = "하루 한번 산책", startDate = LocalDate.now()),
    Challenge(challengeId = 2, title = "책 읽기", startDate = LocalDate.now().minusDays(3)),
    Challenge(challengeId = 3,title = "하루에 물 2리터 마시기", startDate = LocalDate.now().minusDays(9))
)