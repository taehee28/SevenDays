package com.thk.sevendays.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList
import com.thk.data.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    var challenges = challengeRepository.getChallenges().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addChallenge(title: String) = viewModelScope.launch {
        challengeRepository.addChallenge(title)
    }

    fun removeChallenge(id: Long) = viewModelScope.launch {
        challengeRepository.removeChallenge(id)
    }

    fun getChallengeById(id: Long) = challenges.value.firstOrNull { it.challengeId == id }
}