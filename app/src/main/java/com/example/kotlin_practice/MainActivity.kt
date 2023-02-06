package com.example.kotlin_practice

import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.kotlin_practice.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val storage = Firebase.storage("gs://android-with-kotlin-8d5bc.appspot.com")
    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?.let { uploadImage(it) }
    }
    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted->
        if(isGranted) { galleryLauncher.launch("image/*") }
        else { Toast.makeText(this@MainActivity, "외부 저장소 읽기 권한을 승인해야 사용할 수 있습니다.", Toast.LENGTH_SHORT).show() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnUpload.setOnClickListener { permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE) }
        binding.btnDownload.setOnClickListener { downloadImage("images/temp_1675670937747.jpeg") }
    }

    fun uploadImage(uri: Uri){
        val fullPath = makeFilePath("images", "temp", uri)
        val imageRef = storage.getReference(fullPath)
        val uploadTask = imageRef.putFile(uri)

        uploadTask
            .addOnFailureListener { Log.d("LOG_CHECK", "MainActivity :: uploadImage() called :: 실패 : ${it.message}") }
            .addOnSuccessListener { Log.d("LOG_CHECK", "MainActivity :: uploadImage() called :: 성공 : $fullPath") }
    }

    fun makeFilePath(path: String, userId: String, uri: Uri): String {
        val mimeType = contentResolver.getType(uri) ?: "/none" //마임타입 ex) images/jpeg
        val ext = mimeType.split("/")[1] //확장자 ex) jpeg
        val timeSuffix = System.currentTimeMillis() // 시간값 ex)123123123
        return "${path}/${userId}_${timeSuffix}.$ext" // 파일 경로
    }

    fun downloadImage(path:String){
        storage.getReference(path).downloadUrl
            .addOnSuccessListener { uri->
                Glide.with(this@MainActivity).load(uri).into(binding.iv)
            }
            .addOnFailureListener { Log.d("LOG_CHECK", "MainActivity :: downloadImage() called :: 실패 :${it.message}") }
    }
}
