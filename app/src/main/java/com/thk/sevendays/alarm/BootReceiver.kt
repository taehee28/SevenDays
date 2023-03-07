package com.thk.sevendays.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.thk.data.logd
import com.thk.data.repository.SettingsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver: BroadcastReceiver() {

    @Inject lateinit var repository: SettingsRepository
    @Inject lateinit var alarmManager: ReminderAlarmManager

    override fun onReceive(context: Context?, intent: Intent?) {
        requireNotNull(intent)

        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            logd(">> BOOT COMPLETED")

            CoroutineScope(Dispatchers.Default).launch {
                val time = repository.getAlarmTime().first()
                alarmManager.registerAlarm(time)
            }
        }
    }
}