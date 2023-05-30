package com.example.data.models

import com.google.gson.annotations.SerializedName

data class GameDataModel(
    @SerializedName("i") val i: String,
    @SerializedName("d") val d: String,
    @SerializedName("e") val e: List<EDataModel>
)
