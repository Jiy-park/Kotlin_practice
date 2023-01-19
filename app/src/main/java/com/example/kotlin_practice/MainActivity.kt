package com.example.kotlin_practice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.kotlin_practice.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File
import java.util.jar.Manifest
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var cameraPermission:ActivityResultLauncher<String> // 카메라 권한
    lateinit var storagePermission:ActivityResultLauncher<String> // 저장소 권한
    lateinit var cameraLauncher:ActivityResultLauncher<Uri> // 카메라 앱 호출
    lateinit var galleryLauncher:ActivityResultLauncher<String> //갤러리
    var photoUri:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d("LOG_CHECK", "IN ON_CREATE1")
//        setViews()

        storagePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted->
            Log.d("LOG_CHECK", "IN STORAGE_PERMISSION, isGranted : ${isGranted.toString()}")
            if(isGranted == true) { setViews() }
            else{
                Toast.makeText(baseContext, "외부 저장소 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        Log.d("LOG_CHECK", "IN ON_CREATE2")
        cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            Log.d("LOG_CHECK", "IN CAMERA_PERMISSION")
            if(isGranted == true) { openCamera() }
            else {
                Toast.makeText(baseContext, "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
        Log.d("LOG_CHECK", "IN ON_CREATE3")
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){ isSuccess->
            Log.d("LOG_CHECK", "IN CAMERA_LAUNCHER")
            if(isSuccess == true) { binding.ivPrev.setImageURI(photoUri) }
        }
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
            binding.ivPrev.setImageURI(uri)
        }
        storagePermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun setViews(){
        Log.d("LOG_CHECK", "IN SET_VIEWS")
        binding.btnCamera.setOnClickListener {
            cameraPermission.launch(android.Manifest.permission.CAMERA)
        }
        binding.btnGallery.setOnClickListener {
            openGallery()
        }
    }

    fun openCamera(){
        Log.d("LOG_CHECK", "IN OPEN_CAMERA")
        val photoFile = File.createTempFile("IMG_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        photoUri = FileProvider.getUriForFile(this, "${packageName}.provider", photoFile)
        cameraLauncher.launch(photoUri)
    }

    fun openGallery(){
        galleryLauncher.launch("image/*")
    }
}
