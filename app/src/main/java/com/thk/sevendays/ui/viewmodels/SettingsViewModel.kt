package com.thk.sevendays.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.repository.SettingsRepository
import com.thk.sevendays.alarm.ReminderAlarmManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    /*private val alarmManager: ReminderAlarmManager,*/
    private val repository: SettingsRepository
) : ViewModel() {
    val alarmState = repository.isAlarmOn()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun setAlarmState(isOn: Boolean) = viewModelScope.launch {
        repository.saveAlarmState(isOn)
    }
}