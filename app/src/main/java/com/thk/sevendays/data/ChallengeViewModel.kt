package com.thk.sevendays.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList

class ChallengeViewModel: ViewModel() {
    val challenges = mutableStateListOf<Challenge>()

    fun addChallenge(title: String) {
        challenges.add(Challenge(title = title))
    }

    fun getChallengeById(id: Long) = challenges.firstOrNull { it.challengeId == id }
}