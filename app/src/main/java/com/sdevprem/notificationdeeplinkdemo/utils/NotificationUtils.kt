package com.sdevprem.notificationdeeplinkdemo.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.sdevprem.notificationdeeplinkdemo.R
import com.sdevprem.notificationdeeplinkdemo.SecretActivity

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
            .setContentTitle("Secret screen invitation")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentText("Click the action button to enter Secret screen")
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
            "Enter secret screen",
            PendingIntent.getActivity(
                this,
                ENTER_APP_REQUEST_CODE,
                Intent(Intent.ACTION_VIEW, Uri.parse(SecretActivity.SECRET_SCREEN_DEEP_LINK1)),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
            .build()

    fun Context.cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }
}