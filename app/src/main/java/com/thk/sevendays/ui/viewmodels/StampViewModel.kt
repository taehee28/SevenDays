package com.thk.sevendays.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.logd
import com.thk.data.model.Challenge
import com.thk.data.model.Stamp
import com.thk.data.repository.StampRepository
import com.thk.sevendays.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StampViewModel @Inject constructor(
    private val repository: StampRepository
) : ViewModel() {
    private val _challenge = mutableStateOf<Challenge?>(null)
    val challenge: State<Challenge?>
        get() = _challenge

    private val _stamps = MutableStateFlow<List<Stamp>>(emptyList())
    val stamps: StateFlow<List<Stamp>>
        get() = _stamps.asStateFlow()

    fun loadChallengeData(id: Long) {
        logd(">> loadChallengeData: id = $id")
        getChallenge(id)
        getStamps(id)
    }

    private fun getChallenge(id: Long) = viewModelScope.launch {
        _challenge.value = repository.getChallenge(id)
    }

    private fun getStamps(id: Long) = viewModelScope.launch {
        repository.getStamps(id).collectLatest {
            /*uiState.value = UiState.Success(it)*/
            _stamps.value = it
        }
    }

    fun setStampChecked(stamp: Stamp) = viewModelScope.launch {
        repository.setStampChecked(stamp)
    }
}