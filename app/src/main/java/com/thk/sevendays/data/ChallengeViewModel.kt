package com.thk.sevendays.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ChallengeViewModel: ViewModel() {
    var challenges = mutableStateListOf<Challenge>()
        private set

    fun addChallenge(item: Challenge) {
        challenges.add(item)
    }
}