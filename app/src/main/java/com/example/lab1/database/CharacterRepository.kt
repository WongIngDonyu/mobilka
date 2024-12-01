package com.example.lab1.database

import com.example.lab1.enities.CharacterEn

class CharacterRepository(private val characterDao: CharacterDao) {

    fun getAllCharactersFlow(): kotlinx.coroutines.flow.Flow<List<CharacterEn>> {
        return characterDao.getAllCharactersFlow()
    }

    fun getAllCharacters(): List<CharacterEn> {
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