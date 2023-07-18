package com.example.chatbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText
    lateinit var sentbtn:FloatingActionButton
    lateinit var adapter: MessageAdapter
    lateinit var msglist:ArrayList<Message>
    lateinit var dbref : DatabaseReference
    var senderroom:String? =null
    var recieverroom:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        recyclerView = findViewById(R.id.recyclerview_chat_log)
        editText = findViewById(R.id.edittext_chat_log)
        sentbtn = findViewById(R.id.send_button_chat_log)

        dbref = FirebaseDatabase.getInstance().getReference()
        val name=  intent.getStringExtra("name")
        val recieveruid=  intent.getStringExtra("uid")
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        senderroom = recieveruid +senderuid
        recieverroom = senderuid + recieveruid
        msglist = ArrayList()
        adapter = MessageAdapter(this,msglist)
        supportActionBar?.title = name
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        dbref.child("chats").child(senderroom!!).child("message").addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                msglist.clear()
                for (postSnapshot in snapshot.children){
                val message = postSnapshot.getValue(Message::class.java)
                    msglist.add(message!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        sentbtn.setOnClickListener {
            val msg = editText.text.toString()
            val msgObject = Message(msg, senderuid!!)
            dbref.child("chats").child(senderroom!!).child("message").push().setValue(msgObject).addOnSuccessListener {
                dbref.child("chats").child(recieverroom!!).child("message").push().setValue(msgObject)
            }
            editText.setText("")
        }


    }
}