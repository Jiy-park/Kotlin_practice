package com.example.kotlin_practice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        Log.d("LOG_CHECK","action : $action")
        return super.onStartCommand(intent, flags, startId)
    }
    companion object{
        val ACTION_START = "com.example.kotlin_practice.START"
        val ACTION_RUN = "com.example.kotlin_practice.RUN"
        val ACTION_STOP = "com.example.kotlin_practice.STOP"
    }
    override fun onDestroy() {
        Log.d("LOG_CHECK","서비스 종료")
        super.onDestroy()
    }
}