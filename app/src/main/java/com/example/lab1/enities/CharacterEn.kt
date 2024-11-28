package com.example.lab1.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEn(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "culture") val culture: String,
    @ColumnInfo(name = "born") val born: String,
    @ColumnInfo(name = "titles") val titles: String,
    @ColumnInfo(name = "aliases") val aliases: String,
    @ColumnInfo(name = "played_by") val playedBy: String,
) {
    companion object {
        fun from(
            name: String,
            culture: String,
            born: String,
            titles: List<String>,
            aliases: List<String>,
            playedBy: List<String>
        ): CharacterEn {
            return CharacterEn(
                name = name,
                culture = culture,
                born = born,
                titles = titles.joinToString(","),
                aliases = aliases.joinToString(","),
                playedBy = playedBy.joinToString(","),
            )
        }
    }
}
