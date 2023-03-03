@file:OptIn(ExperimentalCoroutinesApi::class)

package com.thk.data.repository

import com.thk.data.datasource.DataStoreSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.time.LocalTime
import javax.inject.Inject

interface SettingsRepository {
    fun isAlarmOn(): Flow<Boolean>
    suspend fun saveAlarmState(isOn: Boolean)
    fun getAlarmTime(): Flow<LocalTime>
    suspend fun saveAlarmTime(time: LocalTime)
}

class SettingsRepositoryImpl @Inject constructor(
    private val dataStoreSource: DataStoreSource
) : SettingsRepository {
    override fun isAlarmOn(): Flow<Boolean> = dataStoreSource.readAlarmState()
        .catch { exception ->
            exception.printStackTrace()
        }

    override suspend fun saveAlarmState(isOn: Boolean) {
        dataStoreSource.saveAlarmState(isOn)
    }

    override fun getAlarmTime(): Flow<LocalTime> = dataStoreSource.readAlarmTime()
        .catch { exception ->
            exception.printStackTrace()
        }
        .map {
            LocalTime.parse(it)
        }

    override suspend fun saveAlarmTime(time: LocalTime) {
        dataStoreSource.saveAlarmTime(time.toString())
    }
}