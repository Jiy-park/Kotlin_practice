package com.example.kotlin_practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.kotlin_practice.databinding.FragmentSenderBinding

class SenderFragment : Fragment() {
    lateinit var binding:FragmentSenderBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSenderBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnYes.setOnClickListener{
            val bundle = bundleOf("valueKey" to "yes")
            setFragmentResult("request", bundle)
        }
        binding.btnNo.setOnClickListener {
            val bundle = bundleOf("valueKey" to "no")
            setFragmentResult("request", bundle)
        }
    }
}