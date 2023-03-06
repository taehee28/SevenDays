package com.thk.sevendays.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.thk.data.logd

class ReminderAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        /*requireNotNull(context) { "Context is null" }
        requireNotNull(intent) { "Intent is null" }*/

        logd(">> onReceive ---------------------------------------------")
    }
}