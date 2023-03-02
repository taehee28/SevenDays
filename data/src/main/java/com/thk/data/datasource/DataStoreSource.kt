package com.thk.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.thk.data.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DataStoreSource {
    fun readAlarmState(): Flow<Boolean>
    suspend fun saveAlarmState(isOn: Boolean)
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCE_NAME)

class DataStoreSourceImpl @Inject constructor(
    context: Context
) : DataStoreSource {
    private companion object PreferenceKeys {
        val alarmState = booleanPreferencesKey(name = Constants.PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    override fun readAlarmState(): Flow<Boolean> = dataStore.data
        .catch { exception ->
            throw exception
        }
        .map { preferences ->
            preferences[PreferenceKeys.alarmState] ?: false
        }

    override suspend fun saveAlarmState(isOn: Boolean) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.alarmState] = isOn
        }
    }
}