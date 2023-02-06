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
import com.example.kotlin_practice.model.User
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
    val usersRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding){
            btnSignin.setOnClickListener { signin() }
            btnSignup.setOnClickListener { signup() }
        }
    }

    fun signup(){
        with(binding){
            val id = edID.text.toString()
            val password = edPassword.text.toString()
            val name = edName.text.toString()

            if(id.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()){
                usersRef.child(id).get().addOnSuccessListener {
                    if(it.exists()) { Toast.makeText(this@MainActivity, "아이디가 존재합니다.", Toast.LENGTH_SHORT).show() }
                    else{
                        val user = User(id, password, name)
                        usersRef.child(id).setValue(user)
                        signin()
                    }
                }
            }
            else { Toast.makeText(this@MainActivity, "아이디 비밀번호 별명을 모두 입력해야 합니다.", Toast.LENGTH_SHORT).show() }
        }
    }

    fun signin(){
        with(binding){
            val id = edID.text.toString()
            val password = edPassword.text.toString()

            if(id.isNotEmpty() && password.isNotEmpty()){
                usersRef.child(id).get().addOnSuccessListener {
                    if(it.exists()){
                        it.getValue(User::class.java)?.let { user->
                            if(user.password == password) { goChatRoomList(user.id, user.name) }
                            else { Toast.makeText(this@MainActivity, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show() }
                        }
                    }
                    else { Toast.makeText(this@MainActivity, "아이디가 존재하지 않습니다.",Toast.LENGTH_SHORT).show() }
                }
            }
            else { Toast.makeText(this@MainActivity, "아이디 비밀번호를 입력해야 합니다.",Toast.LENGTH_SHORT).show() }
        }
    }

    fun goChatRoomList(userId:String, userName:String){
        val intent = Intent(this@MainActivity, ChatListActivity::class.java)

        intent.putExtra("userId", userId)
        intent.putExtra("userName", userName)
        startActivity(intent)
    }
}
