package com.thk.sevendays.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.model.Stamp
import com.thk.data.repository.StampRepository
import com.thk.sevendays.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StampViewModel @Inject constructor(
    private val repository: StampRepository
) : ViewModel() {

    val uiState = MutableStateFlow<UiState<List<Stamp>>>(UiState.Loading)

    fun getStamps(id: Long) = viewModelScope.launch {
        repository.getStamps(id).collectLatest {
            uiState.value = UiState.Success(it)
        }
    }

    fun setStampChecked(stamp: Stamp) = viewModelScope.launch {
        repository.setStampChecked(stamp)
    }

    fun clearUiState() {
        uiState.value = UiState.Loading
    }
}