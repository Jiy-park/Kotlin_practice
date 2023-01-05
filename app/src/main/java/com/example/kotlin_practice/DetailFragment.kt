package com.example.kotlin_practice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_practice.databinding.FragmentDetailBinding
import com.example.kotlin_practice.databinding.FragmentListBinding

class DetailFragment : Fragment() {
    lateinit var mainActivity:MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener { mainActivity?.goBack() }
//        binding.btnBack.setOnClickListener { mainActivity.goBack() } // 타입 캐스팅 형식으로 mainActivity를 초기화 했을 경우 null체크를 안해도 되는듯?
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
//        mainActivity = context as MainActivity //타입 캐스팅 방식
    }
}