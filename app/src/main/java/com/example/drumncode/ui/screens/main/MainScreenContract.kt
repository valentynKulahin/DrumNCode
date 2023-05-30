package com.example.drumncode.ui.screens.main

import com.example.domain.models.GameDomainModel

data class MainScreenContract(
    val result: List<GameDomainModel> = emptyList(),
    val error: String = "",
    val exception: String = "",
    val id: Long = 0
)