package com.example.lab1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter(private var characters: List<CharacterRespons>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val cultureTextView: TextView = itemView.findViewById(R.id.cultureTextView)
        val bornTextView: TextView = itemView.findViewById(R.id.bornTextView)
        val titlesTextView: TextView = itemView.findViewById(R.id.titlesTextView)
        val aliasesTextView: TextView = itemView.findViewById(R.id.aliasesTextView)
        val playedByTextView: TextView = itemView.findViewById(R.id.playedByTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val char = characters[position]
        holder.nameTextView.text = char.name.ifEmpty { "-" }
        holder.cultureTextView.text = char.culture.ifEmpty { "-" }
        holder.bornTextView.text = char.born.ifEmpty { "-" }
        holder.titlesTextView.text = char.titles.joinToString().ifEmpty { "-" }
        holder.aliasesTextView.text = char.aliases.joinToString().ifEmpty { "-" }
        holder.playedByTextView.text = char.playedBy.joinToString().ifEmpty { "-" }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<CharacterRespons>) {
        characters = newData
        notifyDataSetChanged()
    }
}

