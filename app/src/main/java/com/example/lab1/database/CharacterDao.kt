package com.example.lab1.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lab1.enities.CharacterEn

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAllLiveData(): LiveData<List<CharacterEn>>

    @Query("SELECT * FROM characters")
    fun getAll(): List<CharacterEn>

    @Query("SELECT * FROM characters WHERE name = :name AND culture = :culture")
    suspend fun getCharacter(name: String, culture: String): CharacterEn?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterEn: CharacterEn)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterEns: List<CharacterEn>)

    @Delete
    suspend fun delete(characterEn: CharacterEn)

    @Update
    suspend fun update(characterEn: CharacterEn)
}