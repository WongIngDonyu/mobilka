package com.example.lab1.database

import androidx.lifecycle.LiveData
import com.example.lab1.enities.CharacterEn

class CharacterRepository(private val characterDao: CharacterDao) {

    fun getAllCharactersLiveData(): LiveData<List<CharacterEn>> {
        return characterDao.getAllLiveData()
    }

    suspend fun getAllCharacters(): List<CharacterEn> {
        return characterDao.getAll()
    }

    suspend fun getCharacterByNameAndCulture(name: String, culture: String): CharacterEn? {
        return characterDao.getCharacter(name, culture)
    }

    suspend fun insertCharacter(characterEn: CharacterEn) {
        characterDao.insert(characterEn)
    }

    suspend fun insertCharacters(characterEns: List<CharacterEn>) {
        characterDao.insertAll(characterEns)
    }

    suspend fun deleteCharacter(characterEn: CharacterEn) {
        characterDao.delete(characterEn)
    }

    suspend fun updateCharacter(characterEn: CharacterEn) {
        characterDao.update(characterEn)
    }
}