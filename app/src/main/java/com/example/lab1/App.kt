package com.example.lab1

import android.app.Application
import androidx.room.Room
import com.example.lab1.database.CharacterDatabase

class App : Application() {
    private lateinit var db: CharacterDatabase

    override fun onCreate() {
        super.onCreate()
        //deleteDatabase("characters")

        this.db = Room.databaseBuilder(
            this,
            CharacterDatabase::class.java,
            "characters"

        ).build()
    }

    fun getDb(): CharacterDatabase {
        return db
    }
}
