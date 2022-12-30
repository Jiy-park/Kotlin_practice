package com.example.kotlin_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.example.kotlin_practice.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val listener by lazy {
        CompoundButton.OnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                when(button.id){
                    R.id.ch1 -> Log.d("check", "box1_checked")
                    R.id.ch2 -> Log.d("check", "box2_checked")
                    R.id.ch3 -> Log.d("check", "box2_checked")
                }
            }
            else{
                when(button.id){
                    R.id.ch1 -> Log.d("check", "box1_not_checked")
                    R.id.ch2 -> Log.d("check", "box2_not_checked")
                    R.id.ch3 -> Log.d("check", "box3_not_checked")
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tv1.text = "Hello android with Kotlin!"

        binding.group.setOnCheckedChangeListener { radioGroup, checkedid ->
            when(checkedid){
                R.id.rb1 -> Log.d("radio", "btn1_checked")
                R.id.rb2 -> Log.d("radio", "btn2_checked")
                R.id.rb3 -> Log.d("radio", "btn3_checked")
            }
        }
        binding.ch1.setOnCheckedChangeListener(listener)
        binding.ch2.setOnCheckedChangeListener(listener)
//        Thread.sleep(3000) 메인 스레드 (앱 상에 그림을 그리는 스레드)를 잠시 멈춤
        thread(start=true){ // 서브 스레드를 생성 (백그라운드 스레드) 후 해당 스레드를 잠시 멈춤
            Thread.sleep(3000)
            runOnUiThread{ // ui 관련 코드는 메인 스레드에서만 실행 되어야 함
                showProgressBar(false)
            }
        }
    }
    fun showProgressBar(show:Boolean){
        if(show == true) { binding.pb1.visibility = View.VISIBLE }
        else { binding.pb1.visibility = View.GONE }
    }
}
