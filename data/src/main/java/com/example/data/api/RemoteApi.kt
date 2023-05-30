package com.example.data.api

import com.example.data.models.GameDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RemoteApi {

    @GET("api/v1/demo/")
    @Headers("Content-Type: application/json")
    fun getGames(): Call<LinkedHashSet<GameDataModel>>

}