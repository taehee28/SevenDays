package com.thk.sevendays.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.logd
import com.thk.data.model.Challenge
import com.thk.data.repository.ChallengeRepository
import com.thk.sevendays.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    val challenges = challengeRepository
        .getChallenges()
        .distinctUntilChanged()
        .catch { it.printStackTrace() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun addChallenge(title: String) = viewModelScope.launch {
        challengeRepository.addChallenge(title)
    }

    fun removeChallenge(id: Long) = viewModelScope.launch {
        challengeRepository.removeChallenge(id)
    }
}