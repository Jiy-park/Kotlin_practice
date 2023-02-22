package com.example.kotlin_practice;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_practice.databinding.ActivityMainBinding
import com.example.livedata.MyViewModel

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var myViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var cnt = 0

        binding.btn1.setOnClickListener {
            myViewModel.plus()
        }

        binding.btn2.setOnClickListener {
            cnt++
            binding.tv2.text = cnt.toString()
        }

        myViewModel = ViewModelProvider(this@MainActivity)[MyViewModel::class.java]
        myViewModel.value.observe(this@MainActivity, Observer {
            binding.tv1.text = it.toString()
        })
    }
}