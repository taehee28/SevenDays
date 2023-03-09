package com.thk.sevendays.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.thk.data.logd
import com.thk.sevendays.R
import com.thk.sevendays.ui.MainActivity

class ReminderAlarmReceiver : BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_ID = 0
        const val CHANNEL_ID = "com.thk.sevendays.notification"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        logd(">> onReceive")
        requireNotNull(context) { "Context is null" }

        createNotificationChannel(context)
        deliverNotification(context)
    }

    private fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.channel_name)
        val description = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            this.description = description
        }

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).also {
            it.createNotificationChannel(channel)
        }
    }

    private fun deliverNotification(context: Context) {
        // 특정한 composable 화면을 열 수 있도록 deepLink 사용
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "svd://sevendays/Home".toUri(),
            context,
            MainActivity::class.java
        )

        val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_round_sentiment_satisfied_24)
            .setContentTitle(context.getString(R.string.notification_content_title))
            .setContentIntent(deepLinkPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}