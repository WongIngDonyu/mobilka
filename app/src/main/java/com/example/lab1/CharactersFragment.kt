package com.example.lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.database.CharacterRepository
import com.example.lab1.databinding.FragmentCharactersBinding
import com.example.lab1.enities.CharacterEn
import com.example.lab1.network.DataSource
import com.example.lab1.network.KtorNetworkApi
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    private var _ktorApi: KtorNetworkApi? = null
    private val ktorApi get() = _ktorApi ?: throw IllegalStateException("ktorApi is not initialized")

    private val repository: CharacterRepository by lazy {
        val dao = (requireActivity().application as App).getDb().characterDao()
        CharacterRepository(dao)
    }

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
                saveCharactersToDatabase(characters)
            }
        }

        binding.archive.setOnClickListener {
            (binding.characterRecyclerView.adapter as? CharacterAdapter)?.setData(emptyList())
        }
    }

    private suspend fun saveCharactersToDatabase(characters: List<CharacterRespons>) {
        val entities = characters.map {
            CharacterEn.from(
                name = it.name,
                culture = it.culture,
                born = it.born,
                titles = it.titles,
                aliases = it.aliases,
                playedBy = it.playedBy
            )
        }
        repository.insertCharacters(entities)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

