package com.thk.sevendays.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList
import com.thk.data.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    val challenges = mutableStateListOf<Challenge>()

    fun addChallenge(title: String) {
        challenges.add(Challenge(title = title))
    }

    fun getChallengeById(id: Long) = challenges.firstOrNull { it.challengeId == id }
}