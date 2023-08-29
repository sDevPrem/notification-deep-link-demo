package com.sdevprem.notificationdeeplinkdemo

import android.Manifest
import android.content.Intent
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
        handleIntent(intent)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notifyButton.setOnClickListener {
            checkAndNotify()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //this will be called if there is
        //an instance of MainActivity already exist
        //in the current task
        //with the new intent
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) = intent?.data?.toString()?.takeIf {
        //check if the uri of the intent is equal to the deep links
        //of the secret screen
        return@takeIf it == SecretActivity.SECRET_SCREEN_DEEP_LINK2 ||
                it == SecretActivity.SECRET_SCREEN_DEEP_LINK1
    }?.let {
        //if it is the uri of the secret screen
        //remove notification and
        //take the user to the secret screen
        cancelNotification()
        Intent(this, SecretActivity::class.java)
            .also(::startActivity)
        return@let
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