package com.thk.sevendays.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar
import javax.inject.Inject

interface ReminderAlarmManager {
    fun registerAlarm(hour: Hour)
    fun cancelAlarm()
}

class ReminderAlarmManagerImpl @Inject constructor(
    private val alarmManager: AlarmManager,
    private val context: Context
) : ReminderAlarmManager {
    private companion object {
        const val ALARM_REQUEST_CODE = 0
    }

    override fun registerAlarm(hour: Hour) {
        val pendingIntent = createAlarmIntent()

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour.toInt())
            clear(Calendar.MINUTE)
            clear(Calendar.SECOND)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    override fun cancelAlarm() {
        val pendingIntent = createAlarmIntent()

        alarmManager.cancel(pendingIntent)
    }

    private fun createAlarmIntent(): PendingIntent =
        Intent(context, ReminderAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                ALARM_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
}