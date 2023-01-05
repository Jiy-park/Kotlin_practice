package com.example.kotlin_practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.kotlin_practice.databinding.FragmentReceiverBinding


class ReceiverFragment : Fragment() {
    lateinit var binding:FragmentReceiverBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentReceiverBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("request") { key, bundle->
            bundle.getString("valueKey")?.let {
                binding.tv.text = it
            }
        }
    }
}