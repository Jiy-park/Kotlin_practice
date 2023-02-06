package com.example.kotlin_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.example.kotlin_practice.databinding.ActivityChatRoomBinding
import com.example.kotlin_practice.databinding.MsgItemBinding
import com.example.kotlin_practice.model.Message
import com.example.kotlin_practice.model.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class ChatRoomActivity : AppCompatActivity() {
    val binding by lazy { ActivityChatRoomBinding.inflate(layoutInflater) }
    val database = Firebase.database("https://android-with-kotlin-8d5bc-default-rtdb.asia-southeast1.firebasedatabase.app/")
    lateinit var msgRef:DatabaseReference

    var roomId:String = ""
    var roomTitle:String = ""

    val msgList = mutableListOf<Message>()
    lateinit var adapter: MsgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        roomId = intent.getStringExtra("roomId")?:"none"
        roomTitle = intent.getStringExtra("roomTitle")?:"none"
        msgRef = database.getReference("rooms").child(roomId).child("messages")

        adapter = MsgAdapter(msgList)
        with(binding){
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(baseContext)
            tvRoomTitle.text = roomTitle
            btnBack.setOnClickListener { finish() }
            btnSend.setOnClickListener { sendMsg() }
        }
        loadMsgs()
    }

    fun loadMsgs(){
        msgRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                msgList.clear()
                for(item in snapshot.children){
                    item.getValue(Message::class.java)?.let{ msg->
                        msgList.add(msg)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) = print(error.message)
        })
    }

    fun sendMsg(){
        with(binding){
            if(edMessage.text.isNotEmpty()){
                val msg = Message(edMessage.text.toString(), ChatListActivity.userName)
                val msgId = msgRef.push().key!!
                msg.id = msgId
                msgRef.child(msgId).setValue(msg)
                edMessage.text.clear()
            }
        }
    }
}
class MsgAdapter(val msgList:MutableList<Message>):RecyclerView.Adapter<MsgAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MsgItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val msg = msgList[position]
        holder.setMsg(msg)
    }

    override fun getItemCount() = msgList.size

    class Holder(val binding:MsgItemBinding):RecyclerView.ViewHolder(binding.root){
        val sdf by lazy { SimpleDateFormat("yyyy:MM:dd hh:mm:ss") }
        fun setMsg(msg:Message){
            with(binding){
                tvMsgName.text = msg.userName
                tvMsgTxt.text = msg.msg
                tvMsgDate.text = sdf.format(msg.timestamp)
            }
        }
    }
}


