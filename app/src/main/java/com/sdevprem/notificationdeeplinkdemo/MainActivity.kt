package com.sdevprem.notificationdeeplinkdemo

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sdevprem.notificationdeeplinkdemo.databinding.ActivityMainBinding
import com.sdevprem.notificationdeeplinkdemo.utils.NotificationUtils.cancelNotification
import com.sdevprem.notificationdeeplinkdemo.utils.NotificationUtils.isNotificationPermissionGranted
import com.sdevprem.notificationdeeplinkdemo.utils.NotificationUtils.sendNotification

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it)
            runAfterPermissionGranted?.invoke()
    }

    private var runAfterPermissionGranted: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cancelNotification()

        binding.notifyButton.setOnClickListener {
            checkAndNotify()
        }
    }

    private fun checkAndNotify() {
        if (isNotificationPermissionGranted)
            sendNotification()
        else {
            Toast.makeText(
                this,
                "Please allow notification permission in order to show notification",
                Toast.LENGTH_SHORT
            ).show()

            runAfterPermissionGranted = { sendNotification() }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}