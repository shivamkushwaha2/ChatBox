package com.example.chatbox

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(val context:Context,val userlist:ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewholder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewholder(view)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: UserViewholder, position: Int) {
        val currentuser = userlist[position]
        holder.name.text = currentuser.name!!.uppercase()
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("name", currentuser.name!!.uppercase())
            intent.putExtra("uid", currentuser.uid)

            context.startActivity(intent)
        }
    }

    class UserViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name  = itemView.findViewById<TextView>(R.id.users)
        var pic  = itemView.findViewById<CircleImageView>(R.id.pic)


    }
}