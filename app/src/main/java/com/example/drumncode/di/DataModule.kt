package com.example.drumncode.di

import com.example.data.api.RemoteApi
import com.example.data.repos.GamesRepoImpl
import com.example.domain.repos.GamesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesGamesRepo(
        remoteApi: RemoteApi
    ): GamesRepo {
        return GamesRepoImpl(remoteApi = remoteApi)
    }

}