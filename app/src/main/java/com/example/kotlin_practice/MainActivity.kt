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

    
    /**
     * 안드로이드  중요 규칙 "백그라운드 스레드는 UI구성 요소에 접근하면 안된다"
     * EX) activity_main.xml에 텍스트뷰를 하나 만들고 백라운드 스레드를 통해 텍스트 뷰를 업데이트 하면 예외를 발생시키고 앱을 종료함
     * 메인스레드 (UI스레드) 외에는 UI를 업데이트 할 수 없음. 이는 모든 프로그램 공통
     * **/
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var thread1 = WorkerThread() // Thread 객체 이용
        thread1.start()

        var thread2 = Thread(WorkerRunnable()) //Runnabel 인터페이스 이용
        thread2.start()

        Thread{ //람다식으로 Runnable 익명객체 이용
            var i = 0
            while(i < 10){
                i++
                Log.i("LOG_CHECK","lambda : "+i)
            }
        }.start()

        thread(start = true){ //코틀린에서 제공하는 thread 구현 // 얘는 좀 다른데?
            var i = 0
            while(i < 10){
                i++
                Log.i("LOG_CHECK", "thread : "+i)
            }
        }
    }
}
class WorkerThread:Thread(){
    override fun run() {
        var i = 0
        while(i < 10){
            i++
            Log.i("LOG_CHECK","thread : "+i)
        }
    }
}

class WorkerRunnable:Runnable{
    override fun run() {
        var i = 0
        while(i < 10){
            i++
            Log.i("LOG_CHECK","runnable : "+i)
        }
    }
}