package com.example.kotlin_practice

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_practice.databinding.FragmentListBinding

class ListFragment_ : Fragment() {
    var mainActivity:MainActivity? = null
    lateinit var binding:FragmentListBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater,container, false)
        binding.btnNext.setOnClickListener { mainActivity?.goDetail() }
        binding.textString.text = arguments?.getString("key1")
        binding.textInt.text = "${arguments?.getInt("key2")}"
        return binding.root
    }

    fun setValue(value:String){
        binding.textFromActivity.text = value
    }
}