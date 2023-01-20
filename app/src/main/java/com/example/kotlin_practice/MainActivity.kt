package com.example.kotlin_practice

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.kotlin_practice.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL
import java.util.jar.Manifest
import kotlin.concurrent.thread

suspend fun loadImage(imageUrl:String):Bitmap{
    val url = URL(imageUrl)
    val stream = url.openStream()
    return BitmapFactory.decodeStream(stream)
}
class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run{
            btnDownload.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch{
                    progress.visibility = View.VISIBLE
                    val url = editUrl.text.toString()
                    val bitmap = withContext(Dispatchers.IO) {
                        loadImage(url)
                    }
                    imageView.setImageBitmap(bitmap)
                    progress.visibility = View.GONE
                }
            }
        }


//        위의 코드와 같음
//        binding.btnDownload.setOnClickListener {
//            CoroutineScope(Dispatchers.Main).launch {
//                binding.progress.visibility = View.VISIBLE
//                val url = binding.editUrl.text.toString()
//                val bitmap = withContext(Dispatchers.IO){
//                    loadImage(url)
//                }
//                binding.imageView.setImageBitmap(bitmap)
//                binding.progress.visibility = View.GONE
//            }
//        }
    }
}
