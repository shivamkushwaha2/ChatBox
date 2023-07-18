package com.example.chatbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val msglist:ArrayList<Message>): RecyclerView.Adapter<ViewHolder>() {
    val sent = 2
    val recieve =1
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         if (viewType==1){
             val view: View = LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
             return Recieveviewholder(view)
         }else{
             val view: View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
             return Sentviewholder(view)
         }

     }

    override fun getItemCount(): Int {
return msglist.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentmsg = msglist[position]
        if (FirebaseAuth.getInstance().currentUser!!.uid.equals(currentmsg.senderid)){
            return sent
        }else{
            return recieve
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentmsg = msglist[position]
        if (holder.javaClass == Sentviewholder::class.java){
            val viewholder = holder as Sentviewholder
           viewholder.sentmsg.text = currentmsg.message
        }else{
            val viewholder = holder as Recieveviewholder
            viewholder.recievemsg.text = currentmsg.message
        }
    }


    class Sentviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentmsg = itemView.findViewById<TextView>(R.id.textview_to_row)
    }
    class Recieveviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recievemsg = itemView.findViewById<TextView>(R.id.receive)

    }
}