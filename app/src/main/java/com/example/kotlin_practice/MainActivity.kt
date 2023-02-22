package com.example.kotlin_practice;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
        binding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        myViewModel = ViewModelProvider(this@MainActivity)[MyViewModel::class.java]
        binding.myViewModel = myViewModel
        binding.lifecycleOwner = this@MainActivity

//        binding.btn1.setOnClickListener {
//            myViewModel.plus()
//        }

//        myViewModel.value.observe(this@MainActivity, Observer {
//            binding.tv1.text = it.toString()
//        })
    }
}