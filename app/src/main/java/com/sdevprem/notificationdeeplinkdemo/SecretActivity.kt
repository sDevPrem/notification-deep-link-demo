package com.sdevprem.notificationdeeplinkdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SecretActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)
    }

    companion object {
        const val SECRET_SCREEN_DEEP_LINK1 = "dlapp://deep-link.demo/secret-screen"
        const val SECRET_SCREEN_DEEP_LINK2 = "http://deep-link.demo/secret-screen"
    }
}