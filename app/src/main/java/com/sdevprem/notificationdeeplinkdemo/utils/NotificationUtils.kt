package com.sdevprem.notificationdeeplinkdemo.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.sdevprem.notificationdeeplinkdemo.MainActivity
import com.sdevprem.notificationdeeplinkdemo.R

object NotificationUtils {
    private const val NOTIFICATION_CHANNEL_ID = "notification_deep_link_channel_id"
    private const val NOTIFICATION_ID = 23423
    private const val ENTER_APP_REQUEST_CODE = 92384

    val Context.isNotificationPermissionGranted
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        else true

    private val Context.notificationManager
        get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun Context.sendNotification() {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Demo notification Title")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentText("Click the action button to enter app")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(getEnterAppAction())
            .setAutoCancel(true)
            .build()

        if (isNotificationPermissionGranted)
            notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun Context.createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "App Notification",
                NotificationManager.IMPORTANCE_HIGH
            ).also(notificationManager::createNotificationChannel)
    }

    private fun Context.getEnterAppAction() =
        NotificationCompat.Action.Builder(
            R.drawable.ic_notification,
            "Enter App",
            PendingIntent.getActivity(
                this,
                ENTER_APP_REQUEST_CODE,
                Intent(this, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
            .build()

    fun Context.cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }
}