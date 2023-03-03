package com.thk.sevendays.alarm

import android.app.AlarmManager
import javax.inject.Inject

interface ReminderAlarmManager {
    fun registerAlarm(hour: Hour)
    fun cancelAlarm()
}

class ReminderAlarmManagerImpl @Inject constructor(
    private val alarmManager: AlarmManager
) : ReminderAlarmManager {
    override fun registerAlarm(hour: Hour) {
        TODO("Not yet implemented")
    }

    override fun cancelAlarm() {
        TODO("Not yet implemented")
    }
}