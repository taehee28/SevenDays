package com.thk.sevendays.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.logd
import com.thk.data.model.Stamp
import com.thk.data.repository.StampRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StampViewModel @Inject constructor(
    private val repository: StampRepository
) : ViewModel() {
    fun setStampChecked(stamp: Stamp) = viewModelScope.launch {
        repository.setStampChecked(stamp)
    }
}