package com.example.data.repos

import com.example.data.api.RemoteApi
import com.example.data.utils.ApiHandler.handleApi
import com.example.domain.repos.GamesRepo
import com.example.domain.utils.ApiResult
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesRepoImpl @Inject constructor(
    private val remoteApi: RemoteApi
) : GamesRepo {

    override suspend fun getGames(): ApiResult = execute { remoteApi.getGames() }

    private suspend fun execute(request: () -> Call<*>): ApiResult {
        return when (val response = handleApi { request.invoke().execute() }) {
            is ApiResult.ApiSuccess -> ApiResult.ApiSuccess(data = response.data)
            is ApiResult.ApiError -> ApiResult.ApiError(
                code = response.code,
                message = response.message
            )

            is ApiResult.ApiException -> ApiResult.ApiException(e = response.e)
        }
    }

}