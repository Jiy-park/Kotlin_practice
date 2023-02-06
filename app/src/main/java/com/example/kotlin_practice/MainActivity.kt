package com.example.kotlin_practice

import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.kotlin_practice.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task->
            if(task.isSuccessful == false){
                Log.e("토큰", "Fetching FCM registration token failed",task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d("토큰", "재호출 = ${token.toString()}")
        })

    }


}
