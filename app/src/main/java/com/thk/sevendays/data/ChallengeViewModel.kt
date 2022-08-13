package com.thk.sevendays.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.model.Challenge
import com.thk.data.model.sampleChallengeList
import com.thk.data.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    val challenges = challengeRepository.getChallenges().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // TODO: 더 나은 구조가 있는지 알아보기
    val uiState = MutableStateFlow<UiState<List<Challenge>>>(UiState.Loading)

    init {
        viewModelScope.launch {
            challenges.collectLatest {
                uiState.value = if (it != null) UiState.Success(it) else UiState.Loading
            }
        }
    }

    fun addChallenge(title: String) = viewModelScope.launch {
        challengeRepository.addChallenge(title)
    }

    fun removeChallenge(id: Long) = viewModelScope.launch {
        challengeRepository.removeChallenge(id)
    }

    fun getChallengeById(id: Long) = challenges.value?.firstOrNull { it.challengeId == id }
}