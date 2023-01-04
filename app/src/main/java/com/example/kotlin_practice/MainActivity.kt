package com.example.kotlin_practice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kotlin_practice.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this,SubActivity::class.java)
        intent.putExtra("data1", 2023)
        intent.putExtra("data2", "계묘년")

        val return_intent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                val msg = it.data?.getStringExtra("return")
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                binding.tv1Main.text = it.data?.getStringExtra("return")
            }
        }
        binding.btn1Main.setOnClickListener { return_intent.launch(intent) }
    }
}
