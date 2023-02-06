package com.example.kotlin_practice

import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("LOG_CHECK", "MyFirebaseMessagingService :: onNewToken() called :: token:$token")
    }
}