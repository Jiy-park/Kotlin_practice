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
    val database =  Firebase.database("https://android-with-kotlin-8d5bc-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.txtList.text = ""
                for(item in snapshot.children){ // snapshot.children에 user들이 저장되어 있음 그중 하나씩 접근(item)
                    item.getValue(User::class.java)?.let { user-> //getValue로 파이어베이스 데이터를 가져옴 이때 아직 코틀린 클래스가 아니므로 타입캐스팅을 해줌
                        binding.txtList.append("${user.name} : ${user.password}\n")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }
        })

        with(binding){
            btnPost.setOnClickListener {
                val name = edName.text.toString()
                val password = edAge.text.toString()
                val user = User(name, password)
                addItem(user)
            }
        }

    }
    fun addItem(user:User){
        val id = myRef.push().key!!
        user.id = id
        myRef.child(id).setValue(user)
    }
}

class User{
    var id:String = ""
    var name:String = ""
    var password:String = ""

    constructor()
    constructor(name:String, password:String){
        this.name = name
        this.password = password
    }
}