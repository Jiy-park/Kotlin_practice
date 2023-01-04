package com.example.kotlin_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_practice.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    val binding by lazy { ActivitySubBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tv3Sub.text = "${intent.getIntExtra("data1",-1)}"
        binding.tv2Sub.text = intent.getStringExtra("data2")
        binding.btnSubClose.setOnClickListener {
            val return_intent = Intent()
            return_intent.putExtra("return", binding.edSub.text.toString())
            setResult(RESULT_OK, return_intent)
            finish()
        }
    }
}