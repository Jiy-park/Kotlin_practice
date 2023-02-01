package com.example.kotlin_practice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.kotlin_practice.databinding.RecyclerItemBinding


class Holder(val binding : RecyclerItemBinding):RecyclerView.ViewHolder(binding.root){
    fun setUser(user:RepositoryItem?){
        user?.let{
            binding.run {
                tvName.text = it.name
                tvId.text = it.node_id
                Glide.with(iv).load(it.owner.avatar_url).into(iv)
            }

        }
    }
}

class Adapter():RecyclerView.Adapter<Holder>() {
    var user_list:Repository? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = user_list?.get(position)
        holder.setUser(user)
    }

    override fun getItemCount():Int = user_list?.size?:0
}