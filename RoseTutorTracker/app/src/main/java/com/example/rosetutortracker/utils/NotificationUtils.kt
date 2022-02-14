package com.example.rosetutortracker.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.rosetutortracker.MainActivity
import com.example.rosetutortracker.R

object NotificationUtils {

    private const val NOTIFICATION_ID = 1
    private const val channelId = "AlarmNotifierChannelId"
    private const val channelName = "AlarmNotifierChannel"
    private const val MESSAGE_KEY = "message"

    fun createChannel(context: Context) {
        // Required
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            // Options
            setShowBadge(false)
            lockscreenVisibility = Notification.VISIBILITY_SECRET
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(50, 25, 100, 50)
            description = "Alarm notification channel"
        }

        // Required
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun createAndLaunch(
        context: Context,
        data: String,
        title: String = "Rose Tutor Tracker",
        dest: String = "",
        messageKey: String = ""
    ) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        // so that it can launch the app.
        val contentIntent = Intent(context, MainActivity::class.java).also {
            it.putExtra(MESSAGE_KEY, data)
            it.putExtra("fragment", messageKey)
            it.putExtra("tutorid", dest)
        }
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText("$data")
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

}