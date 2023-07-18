package com.example.chatbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.format.SignStyle

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var userlist:ArrayList<User>
    lateinit var adapter: UserAdapter
    lateinit var name: EditText
    lateinit var mAuth: FirebaseAuth
    lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler)
        userlist = ArrayList()
        adapter = UserAdapter(this,userlist)
        mAuth = FirebaseAuth.getInstance()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        dbRef = FirebaseDatabase.getInstance().getReference()

        dbRef.child("user").addValueEventListener(object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (postSnap in snapshot.children){
                    val currentuser = postSnap.getValue(User::class.java)

                    if (mAuth.currentUser!!.uid != currentuser!!.uid){
                        userlist.add(currentuser)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            mAuth.signOut()
            val intent = Intent(this, Login::class.java)
            finish()
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}