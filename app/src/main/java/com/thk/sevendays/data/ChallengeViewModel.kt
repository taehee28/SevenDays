package com.thk.sevendays.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.thk.data.model.Challenge

class ChallengeViewModel: ViewModel() {
    val challenges = mutableStateListOf<Challenge>()

    fun addChallenge(item: Challenge) {
        challenges.add(item)
    }
}