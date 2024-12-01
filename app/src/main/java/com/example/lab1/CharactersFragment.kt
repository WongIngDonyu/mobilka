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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersFragment : Fragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    private var _ktorApi: KtorNetworkApi? = null
    private val ktorApi get() = _ktorApi ?: throw IllegalStateException("ktorApi is not initialized")

    private val characterAdapter = CharacterAdapter(emptyList())

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
        binding.characterRecyclerView.adapter = characterAdapter

        // Проверяем базу данных при запуске
        lifecycleScope.launch {
            val charactersFromDb = withContext(Dispatchers.IO) {
                repository.getAllCharacters()
            }
            if (charactersFromDb.isEmpty()) {// Если база данных пуста, делаем запрос. По умолчанию 15 стр
                val charactersFromApi = ktorApi.getCharacters()
                saveCharactersToDatabase(charactersFromApi)
            }
        }

        // Flow
        lifecycleScope.launchWhenStarted {
            repository.getAllCharactersFlow().collect { charactersFromDb ->
                val characterResponses = charactersFromDb.map {
                    CharacterRespons(
                        name = it.name,
                        culture = it.culture,
                        born = it.born,
                        titles = it.titles.split(",").filter { title -> title.isNotEmpty() },
                        aliases = it.aliases.split(",").filter { alias -> alias.isNotEmpty() },
                        playedBy = it.playedBy.split(",").filter { actor -> actor.isNotEmpty() }
                    )
                }
                characterAdapter.setData(characterResponses)
            }
        }

        // Загрузка новых данных и сохранение в базу
        binding.ktor.setOnClickListener {
            val page = binding.pageInput.text.toString().toIntOrNull()
            if (page != null && page > 0) {
                lifecycleScope.launch {
                    val charactersFromApi = ktorApi.getCharacters(page)
                    saveCharactersToDatabase(charactersFromApi)
                }
            } else {
                binding.pageInput.error = "Введите корректный номер страницы (положительное число)"
            }
        }

        // Очистка списка в адаптере
        binding.clear.setOnClickListener {
            characterAdapter.setData(emptyList())
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
        _ktorApi?.closeClient()
    }
}


