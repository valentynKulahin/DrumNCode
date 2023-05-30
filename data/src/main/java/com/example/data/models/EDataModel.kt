package com.example.data.models

import com.google.gson.annotations.SerializedName

data class EDataModel(
    @SerializedName("d") val d: String,     //who play
    @SerializedName("i") val i: Long,     //match id
    @SerializedName("si") val si: String,    //sport id
    @SerializedName("sh") val sh: String,    //name
    @SerializedName("tt") val tt: Long     //unix time
)
