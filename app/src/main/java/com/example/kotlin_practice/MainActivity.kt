package com.example.kotlin_practice

import android.app.Activity
import android.content.Intent
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
        //스피너
        val item = listOf<String>("select one", "1", "2", "3", "4")
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.tv1Main.text = item[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        //리사이클러뷰
        val data:MutableList<Memo> = createData()
        var adapter2 = CustomAdapter()
        adapter2.list_data = data

        binding.recyclerView.adapter = adapter2
        binding.recyclerView.layoutManager = LinearLayoutManager(this)//세로 스크롤
//        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)//가로 스크롤
//        binding.recyclerView.layoutManager = GridLayoutManager(this,3)//그리드 두번째 인자는 한줄당 표현할 개수
    }

    fun createData():MutableList<Memo>{
        val data:MutableList<Memo> = mutableListOf()
        for(i in 1..100){
            val no:Int = i
            val title:String = "${i}번째 텍스트"
            val date:Long = System.currentTimeMillis()
            data.add(Memo(no,title,date))
        }
        return data
    }
}
