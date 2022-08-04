package com.thk.sevendays.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList

class ChallengeViewModel: ViewModel() {
    val challenges = mutableStateListOf<Challenge>()

    init {
        challenges.addAll(sampleChallengeList)
    }

    // TODO: content에 해당하는 String만 받도록 변경하기
    fun addChallenge(item: Challenge) {
        challenges.add(item)
    }

    fun getChallengeById(id: Int) = challenges.firstOrNull { it.challengeId == id }
}