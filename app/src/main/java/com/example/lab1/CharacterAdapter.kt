package com.example.lab1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.databinding.CharacterBinding

class CharacterAdapter(private var characters: List<CharacterRespons>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(val binding: CharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val char = characters[position]
        with(holder.binding) {
            nameTextView.text = char.name.ifEmpty { "-" }
            cultureTextView.text = char.culture.ifEmpty { "-" }
            bornTextView.text = char.born.ifEmpty { "-" }
            titlesTextView.text = char.titles.joinToString().ifEmpty { "-" }
            aliasesTextView.text = char.aliases.joinToString().ifEmpty { "-" }
            playedByTextView.text = char.playedBy.joinToString().ifEmpty { "-" }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<CharacterRespons>) {
        characters = newData
        notifyDataSetChanged()
    }
}
