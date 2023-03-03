package com.thk.sevendays.alarm

interface ReminderAlarmManager {
    fun registerAlarm(hour: Hour)
    fun cancelAlarm()
}