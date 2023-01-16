package com.example.kotlin_practice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_practice.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter:RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var listData = mutableListOf<RoomMemo>()
    var helper:RoomHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData[position]
        holder.setRoomMemo(memo)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Holder(val binding:ItemRecyclerBinding):RecyclerView.ViewHolder(binding.root){
        var mRoomMemo:RoomMemo? = null

        init{
            binding.btnDelete.setOnClickListener {
                helper?.roomMemoDao()?.delete(mRoomMemo!!)
                listData.remove(mRoomMemo)
                notifyDataSetChanged()
            }
        }
        fun setRoomMemo(memo:RoomMemo){
            this.mRoomMemo = memo
            binding.tvNo.text = "${memo.no}"
            binding.tvContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
            binding.tvDatetime.text = "${sdf.format(memo.datetime)}"
        }
    }
}
