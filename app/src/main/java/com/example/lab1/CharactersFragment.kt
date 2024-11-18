package com.example.lab1

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.databinding.FragmentCharactersBinding
import com.example.lab1.network.DataSource
import com.example.lab1.network.KtorNetworkApi
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CharactersFragment : Fragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    private var _ktorApi: KtorNetworkApi? = null
    private val ktorApi get() = _ktorApi ?: throw IllegalStateException("ktorApi is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        binding.characterRecyclerView.adapter = CharacterAdapter(emptyList())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _ktorApi = DataSource()

        binding.characterRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.ktor.setOnClickListener {
            lifecycleScope.launch {
                val characters: List<CharacterRespons> = ktorApi.getCharacters()
                binding.characterRecyclerView.adapter = CharacterAdapter(characters)
                saveCharactersToFile(characters)
            }
        }

        binding.archive.setOnClickListener {
            (binding.characterRecyclerView.adapter as? CharacterAdapter)?.setData(emptyList())
        }
    }

    private fun saveCharactersToFile(characters: List<CharacterRespons>) { //сохрание инфы в файл
        val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        if (!documentsDir.exists()) {
            documentsDir.mkdirs()
        }
        val fileName = "15.txt"
        val file = File(documentsDir, fileName)
        try {
            FileOutputStream(file).use { outputStream ->
                val content = characters.joinToString(separator = "\n\n") { character ->
                    listOf(
                        "Name: ${character.name.ifEmpty { "Unknown" }}",
                        "Culture: ${character.culture.ifEmpty { "Unknown" }}",
                        "Born: ${character.born.ifEmpty { "Unknown" }}",
                        "Titles: ${character.titles.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "None"}",
                        "Aliases: ${character.aliases.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "None"}",
                        "Played By: ${character.playedBy.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "None"}"
                    ).joinToString("\n")
                }
                outputStream.write(content.toByteArray())
            }
        } catch (e: IOException) {
            Log.e("SaveFile", "Error writing file: ${e.message}")
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
