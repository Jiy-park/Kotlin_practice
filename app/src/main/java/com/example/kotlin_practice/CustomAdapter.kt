package com.example.kotlin_practice

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_practice.databinding.ItemBinding
import java.text.SimpleDateFormat

class CustomAdapter:RecyclerView.Adapter<Holder>() {
    var list_data = mutableListOf<Memo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = list_data[position]
        holder.set_memo(memo)
    }

    override fun getItemCount(): Int {
        return list_data.size
    }
}

class Holder(val binding:ItemBinding):RecyclerView.ViewHolder(binding.root){
    init {
        binding.root.setOnClickListener{
            Toast.makeText(binding.root.context, "클릭된 아이템 : ${binding.textTitle.text}",Toast.LENGTH_SHORT).show()
        }
    }
    fun set_memo(memo:Memo){
        var sdf = SimpleDateFormat("yyyy/MM/dd")

        binding.textNo.text = "${memo.no}"
        binding.textTitle.text = memo.Title
        binding.textDate.text = sdf.format(memo.date)
    }
}