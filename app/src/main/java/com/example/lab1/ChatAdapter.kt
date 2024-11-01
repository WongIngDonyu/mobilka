package com.example.lab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Chat(val name: String, val message: String, val time: String)

class ChatAdapter(private val data: List<Chat>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textView4)
        val message: TextView = itemView.findViewById(R.id.textView5)
        val time: TextView = itemView.findViewById(R.id.textView6)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = data[position]
        holder.name.text = chat.name
        holder.message.text = chat.message
        holder.time.text = chat.time
    }
}