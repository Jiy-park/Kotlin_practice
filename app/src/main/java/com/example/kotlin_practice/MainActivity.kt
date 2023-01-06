package com.example.kotlin_practice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_practice.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val customView = CustomView(this,"안녕하세용")
        binding.frameLayout.addView(customView)
    }
}
class CustomView(context:Context, text:String):View(context){
    val text:String

    init{
        this.text = text
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 100F
        canvas?.drawText(text, 0F, 100F, paint)
        //화면의 왼쪽 상단부터 (x:0, y:0)임
        //또한 drawText가 글자를 그릴 때 글자의 맨왼쪽 아래가 좌표의 시작점
        //textSize == 100F이므로 y좌표도 100F로 설정해야 글자가 안잘림

        val paintCircle = Paint()
        paintCircle.color = Color.BLUE
        val circle = canvas?.drawCircle(100F,100F,50F,paintCircle)

    }
}