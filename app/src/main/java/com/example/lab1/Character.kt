package com.example.lab1

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val name: String = "",
    val culture: String = "",
    val born: String = "",
    val titles: List<String> = emptyList(),
    val aliases: List<String> = emptyList(),
    val playedBy: List<String> = emptyList()
)