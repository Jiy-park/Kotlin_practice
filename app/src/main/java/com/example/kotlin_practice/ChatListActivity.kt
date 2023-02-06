package com.example.kotlin_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.kotlin_practice.databinding.ActivityChatListBinding
import com.example.kotlin_practice.model.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatListActivity : AppCompatActivity() {

    val binding by lazy { ActivityChatListBinding.inflate(layoutInflater) }
    val database = Firebase.database("https://android-with-kotlin-8d5bc-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val roomRef = database.getReference("rooms")
    val roomList = mutableListOf<Room>()
    lateinit var adapter:ChatListAdapter

    companion object{
        var userId:String = ""
        var userName:String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        userId = intent.getStringExtra("userId")?:"none"
        userName = intent.getStringExtra("userName")?:"Anonymous"

        adapter = ChatListAdapter(this.roomList)
        with(binding){
            btnCreate.setOnClickListener { openCreateRoom() }
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(baseContext)
        }
        loadRooms()
    }
    fun openCreateRoom(){
        val editTitle = EditText(this@ChatListActivity)
        val dialog = AlertDialog.Builder(this@ChatListActivity)
            .setTitle("방 이름")
            .setView(editTitle)
            .setPositiveButton("만들기"){ dialog, id->
                createRoom(editTitle.text.toString())
            }
        dialog.show()
    }

    fun createRoom(title: String) {
        val room = Room(title, userName)
        val roomId = roomRef.push().key!!
        room.id = roomId
        roomRef.child(roomId).setValue(room)
    }

    fun loadRooms(){
        roomRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                roomList.clear()
                for(item in snapshot.children){
                    item.getValue(Room::class.java)?.let { room->
                        roomList.add(room)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) = print(error.message)
        })
    }
}

class ChatListAdapter(val roomList:MutableList<Room>): RecyclerView.Adapter<ChatListAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val room = roomList[position]
        holder.setRoom(room)
    }

    override fun getItemCount() = roomList.size

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun setRoom(room:Room){
            itemView.findViewById<TextView>(android.R.id.text1).text = room.title
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatRoomActivity::class.java)
                intent.putExtra("roomId",room.id)
                intent.putExtra("roomTitle",room.title)
                itemView.context.startActivity(intent)
            }
        }
    }
}