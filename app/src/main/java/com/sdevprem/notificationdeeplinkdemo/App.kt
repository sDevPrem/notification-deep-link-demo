package com.sdevprem.notificationdeeplinkdemo

import android.app.Application
import com.sdevprem.notificationdeeplinkdemo.utils.NotificationUtils.createNotificationChannel

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }
}