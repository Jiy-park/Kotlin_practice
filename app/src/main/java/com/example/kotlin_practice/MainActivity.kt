package com.example.kotlin_practice

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.PointerIconCompat.load
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.example.kotlin_practice.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.jar.Manifest
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.getReference("bbs") // 데이터베이스 루트
        myRef.child("name").setValue("Scott") // 루트의 자식 개수 제한은 없는듯?
        myRef.child("age").setValue(19) // 루트의 자식 개수 제한은 없는듯?
        myRef.child("te").setValue("d") // 루트의 자식 개수 제한은 없는듯?
        myRef.child("name").get()// 데이터베이스 읽어오기
            .addOnSuccessListener { Log.d("LOG_CHECK", "MainActivity :: onCreate() called ::  name = ${it.value}") } // 성공
            .addOnFailureListener { Log.d("LOG_CHECK", "MainActivity :: onCreate() called :: error$it") } // 실패

        myRef.child("name").addValueEventListener(object : ValueEventListener{ // 실시간 데이터 조회
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("LOG_CHECK", "MainActivity :: onDataChange() called :: ${snapshot.value}")
                print(snapshot.value)
            }
            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }
        })

    }
}