package com.example.domain.usecase

import com.example.domain.repos.GamesRepo
import javax.inject.Inject

class LoadGamesUseCase @Inject constructor(
    private val gamesRepo: GamesRepo
) {

    suspend fun execute() = gamesRepo.getGames()

}