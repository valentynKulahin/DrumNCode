package com.example.domain.repos

import com.example.domain.utils.ApiResult

interface GamesRepo {

    suspend fun getGames(): ApiResult

}